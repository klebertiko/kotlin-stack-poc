package integration

import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.jupiter.api.Test
import org.kat.module
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class KTorIntegrationTest {

    @Test
    fun `test root api path`() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "/")) {
            assertNotNull(response.content)
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Test
    fun `test get item 0`() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "/api/item/0")) {
            assertNotNull(response.content)
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }
}