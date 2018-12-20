package org.kat

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.kat.controllers.ItemController
import java.util.concurrent.TimeUnit

fun Application.main() {
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost()
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
        gson {
            setPrettyPrinting()
        }
    }

    install(DefaultHeaders)

    install(CallLogging)

    routing {

        val controller = ItemController(org.kat.items)

        get("/") {
            call.respond(mapOf("messages" to messages))
        }
        route(path = "api/item") {
            get(path = "/{id}") {
                call.respond(mapOf("item" to controller.getItem(call.parameters["id"]!!.toInt())))
            }
        }
    }
}

fun main(args: Array<String>) {
    embeddedServer(factory = Netty, environment = commandLineEnvironment(args)).start(wait = true)
}