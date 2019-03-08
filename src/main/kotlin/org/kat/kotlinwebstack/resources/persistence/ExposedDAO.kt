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
import org.kat.kotlinwebstack.resources.toJsonString

object Products: IntIdTable() {
    val name = varchar("name", 50)
}

class Product(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Product>(Products)

    var name by Products.name
}

object Orders: IntIdTable() {
    val product = reference("product", Products)
}

class Order(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Order>(Orders)

    var product by Product referencedOn Orders.product
}

fun main(args: Array<String>) {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create (
            Products,
            Orders
        )

        val bananaProduct = Product.new {
            name = "Banana"
        }

        Order.new {
            product = bananaProduct
        }

        println("Prodcuts: ${Product.all().joinToString { it.name }}")
        println("Orders: ${Order.all().joinToString { it.product.toJsonString() }}")
    }
}