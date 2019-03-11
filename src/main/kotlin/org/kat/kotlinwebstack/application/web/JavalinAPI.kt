package org.kat.kotlinwebstack.application.web

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.javalin.Context
import io.javalin.Javalin
import io.javalin.UnauthorizedResponse
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.security.SecurityUtil
import org.kat.kotlinwebstack.application.web.auth.AuthController
import org.kat.kotlinwebstack.application.web.auth.Roles
import org.kat.kotlinwebstack.application.web.item.ItemController
import org.kat.kotlinwebstack.application.web.message.MessageController
import org.kat.kotlinwebstack.common.javalinModule
import org.kat.kotlinwebstack.resources.JwtService
import org.koin.core.KoinProperties
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import kotlin.reflect.KFunction1

class JavalinAPI(private val port: Int) : KoinComponent {

    private val itemController by inject<ItemController>()
    private val authController by inject<AuthController>()
    private val messageController by inject<MessageController>()
    private val jwtService by inject<JwtService>()
    private val circuitBreaker = CircuitBreakerRegistry.ofDefaults()

    fun init(): Javalin {
        startKoin(listOf(javalinModule), KoinProperties(useEnvironmentProperties = true, useKoinPropertiesFile = true))

        val app = Javalin.create().apply {
            port(port)
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            accessManager { handler, ctx, permittedRoles ->
                val userRole = jwtService.getUserRole(ctx)
                if (permittedRoles.contains(userRole)) {
                    handler.handle(ctx)
                } else {
                    throw UnauthorizedResponse()
                }
            }
        }.start()
        app.enableCorsForAllOrigins()
        return routes(app)
    }

    private fun routes(app: Javalin): Javalin {
        app.routes {
            path("api") {
                path("items") {
                    path(":id") {
                        get { ctx -> itemController.getItem(ctx) }
                    }
                    post("add") { itemController::addItem }
                }
                path("users") {
                    post("login", { ctx -> asJson(ctx, authController::login) }, SecurityUtil.roles(Roles.ANYONE))
                }
                path("messages") {
                    get({ ctx -> messageController.showMessages(ctx) }, SecurityUtil.roles(Roles.ANYONE))
                }
            }
        }
        return app
    }

    private fun asJson(ctx: Context, handler: KFunction1<@ParameterName(name = "ctx") Context, Any>) {
        ctx.json(handler.call(ctx))
    }
}

fun main() {
    JavalinAPI(port = 7000).init()
}