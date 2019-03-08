package org.kat.kotlinwebstack.domain

data class Message(val message: String)

val messages = mutableListOf(
    Message("hello"),
    Message("ola")
)