package org.kat.kotlinwebstack.domain.item

data class Item(val name: String, val quantity: Int)

val items = hashMapOf(
    0 to Item("Pizza", 2),
    1 to Item("Soup", 10),
    2 to Item("Apple", 50)
)