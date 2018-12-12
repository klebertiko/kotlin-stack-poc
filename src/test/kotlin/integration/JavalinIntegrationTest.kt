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
        Fuel.get("http://localhost:7000/api/item/0")
            .response { request, response, result ->
                println(request)
                println(response)
                println(result)

                assertEquals(200, response.statusCode)
            }
    }

    fun `test get item not exist`() {
        Fuel.get("http://localhost:7000/api/item/-1")
            .response { request, response, result ->
                println(request)
                println(response)
                println(result)

                assertEquals(404, response.statusCode)
            }
    }
}