package com.codechauffeur.apimodels

import kotlinx.serialization.Serializable
import com.codechauffeur.models.Item as ItemModel

@Serializable
data class Item(
    val id: Int, val title: String, val author: String, val read: Boolean, val createdAt: String
    )

fun ItemModel.toApiModel(): Item {
    return Item(this.id.value, this.author, this.title, this.read, this.createdAt.toString())
}

@Serializable
data class NewItem(
    val title: String, val author: String
)

@Serializable
data class EditItem(
    val title: String?, val author: String?, val read: Boolean?
)