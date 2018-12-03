package org.kat.models

data class Item(val name: String, val calories: Int)

val items = hashMapOf(
    0 to Item("Pizza", 1000),
    1 to Item("Soup", 10),
    2 to Item("Apple", 50)
)