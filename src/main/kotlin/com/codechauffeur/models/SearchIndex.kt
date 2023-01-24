package com.codechauffeur.models

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.javatime.timestamp

class SearchIndex (id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<SearchIndex>(SearchIndexes)

    var key by SearchIndexes.key
    var items by SearchIndexes.items
    var createdAt by SearchIndexes.createdAt

}

object SearchIndexes: IntIdTable() {
    val key = varchar("key", 255)
    val items = text("items")
    val createdAt = timestamp("createdAt")
}