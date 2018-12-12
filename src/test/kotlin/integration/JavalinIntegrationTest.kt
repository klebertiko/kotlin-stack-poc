package integration

import com.github.kittinunf.fuel.Fuel
import io.javalin.Javalin
import junit.framework.TestCase
import org.kat.JavalinAPI

class JavalinIntegrationTest : TestCase() {

    private lateinit var app: Javalin

    override fun setUp() {
        app = JavalinAPI(port = 7000).init()
    }

    override fun tearDown() {
        app.stop()
    }

    fun `test get item exist`() {
        Fuel.get("http://localhost:7000/api/item/0").response().let {
            println(it.first)
            println(it.second)
            println(it.third)

            assertEquals(200, it.second.statusCode)
        }
    }

    fun `test get item not exist`() {
        Fuel.get("http://localhost:7000/api/item/-1")
            .response().let {
                println(it.first)
                println(it.second)
                println(it.third)

                assertEquals(404, it.second.statusCode)
            }
    }
}