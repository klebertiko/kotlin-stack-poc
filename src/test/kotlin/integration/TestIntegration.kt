package integration

import io.javalin.Javalin
import junit.framework.TestCase
import org.kat.JavalinApp

class TestIntegration : TestCase() {

    private lateinit var app: Javalin
    private val url = "http://localhost:7000/"

    override fun setUp() {
        app = JavalinApp(7000).init()
    }

    override fun tearDown() {
        app.stop()
    }

    fun testDummy() {
        assertEquals(1, 1)
    }
}