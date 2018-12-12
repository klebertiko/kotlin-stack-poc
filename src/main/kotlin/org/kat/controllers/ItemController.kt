package org.kat.controllers

import io.javalin.Context
import org.kat.Item

class ItemController(private val data: Map<Int, Item>) {

    fun getItem(ctx: Context) {
        ctx.pathParam("id").toInt().let {
            data[it]?.let { item ->
                ctx.json(item)
                return
            }
            ctx.status(404)
        }
    }

    fun getItem(id: Int) : Item? {
        id.let {
            data[it]?.let { item ->
                return item
            }
        }
        return null
    }
}