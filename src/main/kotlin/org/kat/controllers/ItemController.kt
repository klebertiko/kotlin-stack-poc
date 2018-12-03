package org.kat.controllers

import io.javalin.Context
import org.kat.models.Item

class ItemController(private val data: Map<Int, Item>) {

    fun getFoodItem(ctx: Context) {
        ctx.pathParam("id").toInt().let {
            data[it]?.let { item ->
                ctx.json(item)
                return
            }
            ctx.status(404)
        }
    }
}