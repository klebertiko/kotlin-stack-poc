package org.kat.kotlinwebstack.resources

import com.github.kittinunf.fuel.core.Client
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import okhttp3.Headers
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okreplay.OkReplayInterceptor
import java.io.ByteArrayOutputStream

class ReplayClient() : Client {
    override fun executeRequest(request: Request): Response {

        val okReplayInterceptor = OkReplayInterceptor()
        val client = OkHttpClient.Builder().addInterceptor(okReplayInterceptor).build()

        var bodyByteArrayOutputStream = ByteArrayOutputStream().apply {
            request.bodyCallback?.invoke(request, this, 0)
        }.toByteArray()

        val request = okhttp3.Request.Builder()
            .url(request.url)
            .method(request.method.value, RequestBody.create(MediaType.parse(request.mediaTypes.joinToString { it }), bodyByteArrayOutputStream))
            .headers(Headers.of(request.headers))
            .build()

        val response = client.newCall(request).execute()
        return Response(url = request.url().url(),
            contentLength = response.body()!!.contentLength(),
            statusCode = response.code(),
            responseMessage = response.message(),
            headers = response.headers().toMultimap(),
            dataStream = response.body()!!.byteStream()
        )
    }
}