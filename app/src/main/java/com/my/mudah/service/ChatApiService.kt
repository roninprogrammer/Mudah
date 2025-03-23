package com.my.mudah.service

import com.my.mudah.model.UserListResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatApiService {
    @GET("users")
    fun getUsers(@Query("page") page: Int = 1): Single<Response<UserListResponse>>

    @POST("users")
    fun sendMessage(@Body body: Map<String, String>): Single<Response<ChatResponse>>
}