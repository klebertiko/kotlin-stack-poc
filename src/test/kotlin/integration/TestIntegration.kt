package integration

import com.github.kittinunf.fuel.Fuel
import io.ktor.server.engine.ApplicationEngine
import junit.framework.TestCase
import org.junit.jupiter.api.Test
import org.kat.KtorApp
import java.util.concurrent.TimeUnit

class TestIntegration : TestCase() {

    private lateinit var ktorApp: ApplicationEngine

    override fun setUp() {
        ktorApp = KtorApp(8000).init()
    }

    override fun tearDown() {
        ktorApp.stop(5, 5, TimeUnit.SECONDS)
    }

    @Test
    fun testGetItemExists() {
        Fuel.get("http://localhost:8000/api/item/0")
            .response { request, response, result ->
                println(request)
                println(response)
                println(result)

                println("response.statusCode: ${response.statusCode}")
                assertEquals(200, response.statusCode)
            }
    }

    @Test
    fun testGetItemNotExist() {
        Fuel.get("http://localhost:8000/api/item/-1")
            .response { request, response, result ->
                println(request)
                println(response)
                println(result)

                println("response.statusCode: ${response.statusCode}")
                assertEquals(404, response.statusCode)
            }
    }
}