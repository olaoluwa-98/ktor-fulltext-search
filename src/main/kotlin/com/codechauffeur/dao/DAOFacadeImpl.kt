package com.codechauffeur.dao

import com.codechauffeur.models.*
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insertIgnore


class DAOFacadeImpl : DAOFacade {
    override suspend fun allItems(): List<Item> {
        return transaction { Item.all().toList() }
    }

    override suspend fun getItemById(id: Int): Item? {
        return transaction { Item.findById(id) }
    }

    fun addToIndex (item: Item) {
        var keys = listOf<String>();
        keys = keys.plus(item.title.split("\\s+".toRegex()).toTypedArray())
        keys = keys.plus(item.author.split("\\s+".toRegex()).toTypedArray())
        keys = keys.toSet().toList();

        keys.forEach {
            transaction {
                val index = SearchIndex.find{
                    SearchIndexes.key eq  it.uppercase()
                }
                if(index.empty()) {
                    SearchIndex.new {
                        key = it.uppercase();
                        items = Json.encodeToString(listOf(item.id.value))
                        createdAt = Instant.now()
                    }
                } else {
                    var itemIds = Json.decodeFromString<List<Int>>(index.first().items).toList()
                    itemIds = itemIds.plus(item.id.value)
                    index.first().items = Json.encodeToString(itemIds)
                }
            }
        }
    }

    override suspend fun addNewItem(inputTitle: String, inputAuthor: String): Item {
        val item = transaction { Item.new {
                title = inputTitle
                author = inputAuthor
                read = false
                createdAt = Instant.now()
            }
        }
        this.addToIndex(item)
        return item;
    }

    override suspend fun editItemById(id: Int, inputTitle: String?, inputAuthor: String?, inputRead: Boolean?) {
        val item = getItemById(id) ?: throw NotFoundException()
        transaction {
            if(inputAuthor != null){
                item.author = inputAuthor
            }
            if(inputTitle != null){
                item.title = inputTitle
            }
            if(inputRead != null){
                item.read = inputRead
            }
        }
    }

    override suspend fun deleteItemById(id: Int): Boolean {
        val item = getItemById(id)
        if(item != null) {
            transaction {
                item.delete()
            }
            return true
        }
        return false
    }

    override suspend fun searchItem(key: String): List<Item> {
        return transaction {
            val index = SearchIndex.find {
                SearchIndexes.key eq key.uppercase()
            }
            if (index.empty()) return@transaction emptyList();

            val itemIds = Json.decodeFromString<List<Int>>(index.first().items).toList()

            return@transaction Item.forIds(itemIds).toList();
        }
    }
}