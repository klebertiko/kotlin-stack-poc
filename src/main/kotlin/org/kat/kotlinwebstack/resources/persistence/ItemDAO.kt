package org.kat.kotlinwebstack.resources.persistence

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.kat.kotlinwebstack.domain.item.Item

object Items: IntIdTable() {
    val name = varchar("name", 50)
    val quantity = integer("quantity")
}

class Item(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<org.kat.kotlinwebstack.resources.persistence.Item>(Items)

    var name by Items.name
    var quantity by Items.quantity
}

class ItemRepository {

    init {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
    }

    fun create(item: Item) {
        transaction {
            SchemaUtils.create(Items)
            val item = org.kat.kotlinwebstack.resources.persistence.Item.new {
                name = item.name
                quantity = item.quantity
            }
        }
    }
}