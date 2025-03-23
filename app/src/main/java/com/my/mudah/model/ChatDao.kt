package com.my.mudah.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    fun getAllMessages(): Flowable<List<ChatMessage>>

    @Insert
    fun insertMessage(msg: ChatMessage)
}
