package com.codechauffeur

import com.codechauffeur.dao.DatabaseFactory
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.codechauffeur.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()
    configureMonitoring()
    configureHTTP()
    configureRouting()
    configureSerialization()
}
