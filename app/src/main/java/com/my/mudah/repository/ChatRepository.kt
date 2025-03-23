package com.my.mudah.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.room.Insert
import com.my.mudah.model.ChatDao
import com.my.mudah.model.ChatMessage
import com.my.mudah.service.ChatApiService
import com.my.mudah.service.ChatResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val api: ChatApiService,
    private val dao: ChatDao
) {
    fun getMessages(): Flowable<List<ChatMessage>> = dao.getAllMessages()
    @Insert
    fun insertMessage(msg: ChatMessage) {
        Completable.fromAction { dao.insertMessage(msg) }
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("ChatRepository", "Message inserted.")
            }, {
                Log.e("ChatRepository", "Error inserting message", it)
            })
    }
    fun sendMessageToApi(msg: ChatMessage): Single<Response<ChatResponse>> =
        api.sendMessage(mapOf("message" to msg.message))
}
