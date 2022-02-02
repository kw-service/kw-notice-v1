package dev.yjyoon.kwnotice.data.remote.api

import dev.yjyoon.kwnotice.util.Constants.Companion.API_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton retrofit instance
object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val noticeApi: NoticeService by lazy {
        retrofit.create(NoticeService::class.java)
    }
}