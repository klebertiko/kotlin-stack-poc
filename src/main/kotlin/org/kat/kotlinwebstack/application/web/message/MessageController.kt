package org.kat.kotlinwebstack.application.web.message

import io.javalin.Context
import org.kat.kotlinwebstack.domain.message.messages

class MessageController {

    fun showMessages(ctx: Context): Context {
        return ctx.json(messages)
    }
}