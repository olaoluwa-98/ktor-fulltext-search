package com.codechauffeur.dao

import com.codechauffeur.models.*

interface DAOFacade {
    suspend fun allItems(): List<Item>
    suspend fun getItemById(id: Int): Item?
    suspend fun addNewItem(title: String, author: String): Item?
    suspend fun editItemById(id: Int, title: String?, author: String?, read: Boolean?)
    suspend fun deleteItemById(id: Int): Boolean
    suspend fun searchItem(key: String): List<Item>
}