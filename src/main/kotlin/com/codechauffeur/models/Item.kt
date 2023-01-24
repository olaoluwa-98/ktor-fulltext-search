package com.codechauffeur.models

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.javatime.timestamp

class Item (id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Item>(Items)

    var title by Items.title
    var author by Items.author
    var read by Items.read
    var createdAt by Items.createdAt
}

object Items: IntIdTable() {
    val title = varchar("title", 255)
    val author = varchar("author", 255)
    val read = bool("read")
    val createdAt = timestamp("createdAt")
}