package com.codechauffeur.services

import com.codechauffeur.apimodels.Item
import com.codechauffeur.models.Item as ItemDBModel
import com.codechauffeur.dao.DAOFacadeImpl
import com.codechauffeur.apimodels.toApiModel

class ItemService {
    private val dao = DAOFacadeImpl();

    suspend fun getAllItems(): List<Item> {
        return dao.allItems().map { it.toApiModel() }
    }

    suspend fun searchItem(searchTerm: String): List<Item> {
        val keys = searchTerm.split("\\s+".toRegex()).toTypedArray()
        if(keys.isEmpty()) return emptyList();

        var finalList = listOf<ItemDBModel>()

        keys.forEach {
            val res = dao.searchItem(it)
            finalList = finalList.plus(res)
        }
        return finalList.toSet().map { it.toApiModel() }
    }

    suspend fun getItemById(id: Int): Item? {
        return dao.getItemById(id)?.toApiModel()
    }

    suspend fun addItem(title: String, author: String): Int {
        return dao.addNewItem(title, author).id.value
    }

    suspend fun editItem(id: Int, title: String?, author: String?, read: Boolean?) {
        dao.editItemById(id, title, author, read)
    }
}