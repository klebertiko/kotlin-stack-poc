package integration

import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.jupiter.api.Test
import org.kat.kotlinwebstack.application.web.main
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class KTorIntegrationTest {

    @Test
    fun `test root api path`() = withTestApplication(Application::main) {
        with(handleRequest(HttpMethod.Get, "/")) {
            assertNotNull(response.content)
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Test
    fun `test get item 0`() = withTestApplication(Application::main) {
        with(handleRequest(HttpMethod.Get, "/api/items/0")) {
            assertNotNull(response.content)
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }
}