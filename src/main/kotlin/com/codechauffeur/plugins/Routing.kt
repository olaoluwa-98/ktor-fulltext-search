package com.codechauffeur.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.Application
import com.codechauffeur.routes.*

fun Application.configureRouting() {
    routing {
        itemRouting()
    }
}