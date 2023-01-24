package com.codechauffeur.dao

import com.codechauffeur.models.Items
import com.codechauffeur.models.SearchIndexes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*

object DatabaseFactory {
    fun init() {
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:./build/db"
        val database = Database.connect(jdbcURL, driverClassName)

        transaction(database) {
            SchemaUtils.create(Items)
            SchemaUtils.create(SearchIndexes)
        }
    }
}