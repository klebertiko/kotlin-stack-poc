package org.kat.kotlinwebstack.resources.persistence

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

object Products: IntIdTable() {
    val name = varchar("name", 50)
    val price = decimal("price", Int.MAX_VALUE, 2)
}

class Product(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Product>(Products)

    var name by Products.name
    var price by Products.price
}

object Orders: IntIdTable() {
    val number = integer(name = "number")
    val product = reference("product", Products)
}

class Order(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Order>(Orders)

    var number by Orders.number
    var product by Product referencedOn Orders.product
}

fun main(args: Array<String>) {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Products, Orders)

        val bananaProduct = Product.new {
            name = "Banana"
            price = 10.2.toBigDecimal()
        }

        val appleProduct = Product.new {
            name = "Apple"
            price = 20.2.toBigDecimal()
        }

        Order.new {
            number = 1
            product = bananaProduct
        }

        Order.new {
            number = 2
            product = appleProduct
        }

        println("Prodcuts\n-----------------\n${Product.all().joinToString { "${it.name}" }}")
        println("Orders\n-----------------\n${Order.all().joinToString { "Number: ${it.number}\tItem: ${it.product.name}" }}")
    }
}