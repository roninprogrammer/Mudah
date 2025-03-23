package com.my.mudah.service

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatApiService {
    @POST("users")
    fun sendMessage(@Body body: Map<String, String>): Single<Response<ChatResponse>>
}