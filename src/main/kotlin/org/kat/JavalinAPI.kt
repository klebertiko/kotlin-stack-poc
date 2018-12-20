package org.kat

import io.javalin.Context
import io.javalin.Javalin
import io.javalin.UnauthorizedResponse
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.security.Role
import io.javalin.security.SecurityUtil
import org.kat.controllers.AuthController
import org.kat.controllers.ItemController
import org.kat.util.JwtProvider
import kotlin.reflect.KFunction1

internal enum class Roles : Role {
    ANYONE, AUTHENTICATED
}

class JavalinAPI(private val port: Int) {

    private val controller = ItemController()
    private val authController = AuthController()

    fun init(): Javalin {

        val app = Javalin.create().apply {
            port(port)
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            accessManager { handler, ctx, permittedRoles ->
                val userRole = getUserRole(ctx)
                if (permittedRoles.contains(userRole)) {
                    handler.handle(ctx)
                } else {
                    throw UnauthorizedResponse()
                }
            }
        }.start()

        app.get("/") { ctx -> ctx.json(mapOf("messages" to messages)) }

        app.routes {
            path("api") {
                path("items") {
                    path(":id") {
                        get { ctx -> controller.getItem(ctx) }
                    }
                }
                path("users") {
                    post("login", { ctx -> asJson(ctx, authController::login) }, SecurityUtil.roles(Roles.ANYONE))
                }
            }
        }
        return app
    }

    private fun getUserRole(ctx: Context): Role {
        val jwtToken = getTokenHeader(ctx)
        if (jwtToken.isNullOrBlank()) {
            return Roles.ANYONE
        }

        val userRole = JwtProvider.decodeJWT(jwtToken).getClaim("role").asString()

        return Roles.valueOf(userRole)
    }

    private fun getTokenHeader(ctx: Context): String? {
        return ctx.header(header = "Authorization")?.substringAfter(delimiter = "Token")?.trim()
    }

    private fun asJson(ctx: Context, handler: KFunction1<@ParameterName(name = "ctx") Context, Any>) {
        ctx.json(handler.call(ctx))
    }
}

fun main(args: Array<String>) {
    JavalinAPI(port = 7000).init()
}