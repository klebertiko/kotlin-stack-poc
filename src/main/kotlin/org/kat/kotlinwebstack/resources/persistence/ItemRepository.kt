package org.kat.kotlinwebstack.resources.persistence

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.kat.kotlinwebstack.domain.item.Item
import org.kat.kotlinwebstack.domain.item.ItemDTO
import org.kat.kotlinwebstack.domain.item.Items

class ItemRepository {

    init {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
    }

    fun create(itemDTO: ItemDTO) {
        transaction {
            SchemaUtils.create(Items)
            Item.new {
                name = itemDTO.name
                quantity = itemDTO.quantity
            }
        }
    }

    fun retrieve() {
        transaction {
            SchemaUtils.create(Items)
            Item.all()
        }
    }

    fun retrieve(itemId: Int) {
        transaction {
            SchemaUtils.create(Items)
            Item.findById(itemId)
        }
    }
}