package org.kat.kotlinwebstack.application.web.item

import io.javalin.Context
import org.kat.kotlinwebstack.domain.item.ItemDTO
import org.kat.kotlinwebstack.domain.item.items
import org.kat.kotlinwebstack.resources.persistence.ItemRepository
import org.kat.kotlinwebstack.resources.utils.toJsonObject
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ItemController : KoinComponent {

    private val itemRepository by inject<ItemRepository>()

    fun getItem(ctx: Context) {
        ctx.pathParam("id").toInt().let {
            items[it]?.let { item ->
                ctx.json(item)
                return
            }
            ctx.status(404)
        }
    }

    fun addItem(ctx: Context) {
        ctx.validatedBody<ItemDTO>()
        itemRepository.create(ctx.body().toJsonObject(ItemDTO::class.java))
        return
    }

    fun getItem(id: Int) : ItemDTO? {
        id.let {
            items[it]?.let { item ->
                return item
            }
        }
        return null
    }
}