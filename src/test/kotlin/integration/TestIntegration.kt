package integration

import com.github.kittinunf.fuel.Fuel
import io.javalin.Javalin
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import org.kat.JavalinApp
import org.kat.models.Item
import org.kat.models.items
import util.toJsonString

class TestIntegration : TestCase() {

    @MockK
    lateinit var itemMock: Item

    private lateinit var app: Javalin

    override fun setUp() {
        app = JavalinApp(7000).init()
    }

    override fun tearDown() {
        app.stop()
    }

    fun testDummy() {
        assertEquals(1, 1)
    }

    fun testGetItemExists() {
        Fuel.get("http://localhost:7000/api/item/0")
            .response { request, response, result ->
                println(request)
                println(response)
                println(result)

                val item = response.data.toJsonString()
                assertEquals(items[0], item)
                assertEquals(200, response.statusCode)
            }

    }

    fun testGetItemNotExist() {
        Fuel.get("http://localhost:7000/api/item/-1")
            .response { request, response, result ->
                println(request)
                println(response)
                println(result)

                assertEquals(404, response.statusCode)
            }
    }
}