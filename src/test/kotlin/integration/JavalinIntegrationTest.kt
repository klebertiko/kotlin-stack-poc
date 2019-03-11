package integration

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Client
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import io.javalin.Javalin
import junit.framework.TestCase
import okhttp3.OkHttpClient
import okreplay.OkReplayInterceptor
import org.kat.kotlinwebstack.application.web.JavalinAPI

class JavalinIntegrationTest : TestCase() {

    private lateinit var app: Javalin

    override fun setUp() {
        app = JavalinAPI(port = 7000).init()
    }

    override fun tearDown() {
        app.stop()
    }

    fun `test get item exist`() {
        Fuel.get("http://localhost:7000/api/items/0").response().let {
            println(it.first)
            println(it.second)
            println(it.third)

            assertEquals(200, it.second.statusCode)
        }
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

class ReplayClient() : Client {
    override fun executeRequest(request: Request): Response {

        val client = OkHttpClient.Builder().addInterceptor(OkReplayInterceptor()).build()

        val request = okhttp3.Request.Builder()
            .url(request.url)
            .method(request.method.value, )
            .build()

        val response = client.newCall(request).execute()
        return Response(url = request.url().url(),
            contentLength = response.body().contentLength(),
            statusCode = response.code(),
            responseMessage = response.message(),
            headers = response.headers().toMultimap(),
            dataStream = response.body()!!.byteStream()
        )
    }
}