package integration

import io.ktor.application.Application
import io.ktor.server.testing.withTestApplication
import org.junit.jupiter.api.Test
import org.kat.module

class TestIntegration {

    @Test
    fun `sample test`() = withTestApplication(Application::module){

    }

    @Test
    fun `test get item exist`() {
//        Fuel.get("http://localhost:8000/api/item/0")
//            .response { request, response, result ->
//                println(request)
//                println(response)
//                println(result)
//
//                println("response.statusCode: ${response.statusCode}")
//                assertEquals(200, response.statusCode)
//            }
    }

//    @Test
//    fun `test get item not exist`() {
//        Fuel.get("http://localhost:8000/api/item/-1")
//            .response { request, response, result ->
//                println(request)
//                println(response)
//                println(result)
//
//                println("response.statusCode: ${response.statusCode}")
//                assertEquals(404, response.statusCode)
//            }
//    }
}