package org.kat

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import org.kat.controllers.ItemController

class JavalinAPI(private val port: Int) {

    private val controller = ItemController(items)

    fun init(): Javalin {

        val app = Javalin.create().apply {
            port(port)
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
        }.start()

        app.get("/") { ctx -> ctx.json(mapOf("messages" to messages)) }

        app.routes {
            path("api") {
                path("item") {
                    path(":id") {
                        get { ctx -> controller.getItem(ctx) }
                    }
                }
            }
        }
        return app
    }
}

fun main(args: Array<String>) {
    JavalinAPI(port = 7000).init()
}