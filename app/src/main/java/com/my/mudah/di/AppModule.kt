package com.my.mudah.di

import android.content.Context
import androidx.room.Room
import com.my.mudah.database.AppDatabase
import com.my.mudah.model.ChatDao
import com.my.mudah.model.MockInterceptor
import com.my.mudah.repository.ChatRepository
import com.my.mudah.service.ChatApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideChatApiService(@ApplicationContext context: Context): ChatApiService {
        val mockInterceptor = MockInterceptor(context)

        val client = OkHttpClient.Builder()
            .addInterceptor(mockInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://reqres.in/api/") // still needs a base URL
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ChatApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "chat_db"
        ).build()
    }

    @Provides
    fun provideChatDao(database: AppDatabase): ChatDao = database.chatDao()

    @Provides
    fun provideChatRepository(api: ChatApiService, dao: ChatDao): ChatRepository = ChatRepository(api, dao)
}