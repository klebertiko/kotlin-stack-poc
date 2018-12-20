package org.kat.controllers

import io.javalin.Context
import org.kat.Item
import org.kat.items

class ItemController {

    fun getItem(ctx: Context) {
        ctx.pathParam("id").toInt().let {
            items[it]?.let { item ->
                ctx.json(item)
                return
            }
            ctx.status(404)
        }
    }

    fun getItem(id: Int) : Item? {
        id.let {
            items[it]?.let { item ->
                return item
            }
        }
        return null
    }
}