package com.codechauffeur.routes

import com.codechauffeur.services.ItemService
import com.codechauffeur.apimodels.NewItem
import com.codechauffeur.apimodels.EditItem
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val service = ItemService()

fun Route.itemRouting() {
    route("items") {
        get {
            val res = service.getAllItems()
            call.respond(res)
        }

        get("search") {
            val res = service.searchItem(call.request.queryParameters["query"] ?: "")
            call.respond(res)
        }

        post (){
            val newItem = call.receive<NewItem>()
            val id = service.addItem(newItem.title, newItem.author)
            call.respond("Item added successfully. id = $id")
        }

        put ("{id}"){
            val id = call.parameters["id"]?.toInt()
            if(id == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                val editItem = call.receive<EditItem>()
                service.editItem(id, editItem.title, editItem.author, editItem.read)
                call.respond("Item edited successfully")
            }
        }

        get("{id}") {
            val id = call.parameters["id"]?.toInt()
            if(id == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                val res = service.getItemById(id)
                if(res != null){
                    call.respond(res)
                }
            }
        }
    }
}