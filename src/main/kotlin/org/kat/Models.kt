package org.kat

data class Item(val name: String, val quantity: Int)

val items = hashMapOf(
    0 to Item("Pizza", 2),
    1 to Item("Soup", 10),
    2 to Item("Apple", 50)
)

data class Message(val message: String)

val messages = mutableListOf(
    Message("hello"),
    Message("ola")
)