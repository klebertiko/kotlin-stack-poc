package org.kat.kotlinwebstack.domain.item

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

val items = hashMapOf(
    0 to ItemDTO("Pizza", 2),
    1 to ItemDTO("Soup", 10),
    2 to ItemDTO("Apple", 50)
)

object Items: IntIdTable() {
    val name = varchar("name", 50)
    val quantity = integer("quantity")
}

class Item(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Item>(Items)

    var name by Items.name
    var quantity by Items.quantity
}

data class ItemDTO(val name: String, val quantity: Int)