package com.my.mudah.model

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody

class MockInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url().toString()

        val responseString = when {
            uri.endsWith("/users") -> loadJSONFromAsset("preloadedChat.json")
            else -> "{}"
        }

        return chain.proceed(chain.request())
            .newBuilder()
            .code(200)
            .message(responseString)
            .body(
                ResponseBody.create(MediaType.parse("application/json"), responseString)

            )
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun loadJSONFromAsset(fileName: String): String {
        val inputStream = context.assets.open(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }
}
