package com.my.mudah.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.my.mudah.model.ChatDao
import com.my.mudah.model.ChatMessage

@Database(entities = [ChatMessage::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
}
