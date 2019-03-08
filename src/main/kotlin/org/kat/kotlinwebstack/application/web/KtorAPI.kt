package org.kat.kotlinwebstack.application.web

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.authenticate
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
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
import org.kat.kotlinwebstack.application.web.auth.AuthController
import org.kat.kotlinwebstack.application.web.item.ItemController
import org.kat.kotlinwebstack.domain.message.messages
import org.kat.kotlinwebstack.resources.JwtProvider

fun Application.main() {

    val jwtIssuer = environment.config.property("jwt.domain").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtRealm = environment.config.property("jwt.realm").getString()

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

    install(Authentication) {
        jwt {
            realm = jwtRealm
            verifier(JwtProvider.verify(jwtIssuer))
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
        }
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

        val itemController = ItemController()
        val authController = AuthController()

        get("/") {
            call.respond(mapOf("messages" to messages))
        }

        authenticate {
            route(path = "api/items") {
                get(path = "/{id}") {
                    call.respond(mapOf("item" to itemController.getItem(call.parameters["id"]!!.toInt())))
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    embeddedServer(factory = Netty, environment = commandLineEnvironment(args)).start(wait = true)
}