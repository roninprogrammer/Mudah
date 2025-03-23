package com.my.mudah.model

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

class MockInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.toString()
        val method = request.method

        if (url.contains("/users") && method == "POST") {
            val responseString = loadJSONFromAsset("preloadedChat.json")
            return Response.Builder()
                .code(200)
                .message("OK")
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .body(responseString.toResponseBody("application/json".toMediaTypeOrNull()))
                .addHeader("content-type", "application/json")
                .build()
        }

        return chain.proceed(request)
    }

    private fun loadJSONFromAsset(fileName: String): String {
        val inputStream = context.assets.open(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }
}
