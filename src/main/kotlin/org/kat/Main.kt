package org.kat

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.kat.controllers.ItemController
import org.kat.models.items

fun main(args: Array<String>) {

//    JavalinApp(7000).init()
    KtorApp(8000).init()
}

class KtorApp(private val port: Int) {

    fun init() {
        embeddedServer(Netty, port) {
            routing {
                get("/") {
                    call.respondText("Hello World!", ContentType.Text.Html)
                }
            }
        }.start(wait = true)
    }
}

class JavalinApp(private val port: Int) {

    fun init(): Javalin {

        val app = Javalin.create().apply {
            port(port)
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
        }.start()

        app.get("/") { ctx -> ctx.result("Hello World") }

        val controller = ItemController(items)

        app.routes {
            path("api") {
                path("item") {
                    path(":id") {
                        get { ctx -> controller.getFoodItem(ctx) }
                    }
                }
            }
        }

        return app
    }
}