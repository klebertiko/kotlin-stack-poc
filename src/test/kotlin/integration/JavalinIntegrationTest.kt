package integration

import com.github.kittinunf.fuel.Fuel
import io.javalin.Javalin
import junit.framework.TestCase
import org.http4k.core.*
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.TrafficFilters
import org.http4k.traffic.ReadWriteStream
import org.http4k.traffic.Responder
import org.kat.kotlinwebstack.application.web.JavalinAPI


class JavalinIntegrationTest : TestCase() {

//    private lateinit var app: Javalin

    override fun setUp() {
//        app = JavalinAPI(port = 7000).init()
    }

    override fun tearDown() {
//        app.stop()
    }

    fun `test get item exist`() {
        val storage = ReadWriteStream.Disk("recorded")

        // wrap any HTTP Handler in a Recording Filter and play traffic through it
//        val recording = TrafficFilters.RecordTo(storage).then { Response(OK).body("{\"name\":\"Pizza\",\"quantity\":2}") }
//        recording(Request(Method.GET, "http://localhost:7000/api/items/0"))

        // now set up a responder
        val handler = Responder.from(storage)

        // the responder will replay the responses in order
        println("MEU NARIZ ${handler(Request(Method.GET, "http://localhost:7000/api/items/0"))}")


//        Fuel.get("http://localhost:7000/api/items/0").response().let {
//            println(it.first)
//            println(it.second)
//            println(it.third)
//
//            assertEquals(200, it.second.statusCode)
//        }
    }

    fun `test get item not exist`() {
        Fuel.get("http://localhost:7000/api/items/-1")
            .response().let {
                println(it.first)
                println(it.second)
                println(it.third)

                assertEquals(404, it.second.statusCode)
            }
    }
}