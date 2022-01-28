package dev.yjyoon.kwnotice.data.remote.api

import dev.yjyoon.kwnotice.data.remote.model.KwHomeNotice
import retrofit2.Call
import retrofit2.http.GET

interface NoticeService {
    @GET("kw-home")
    fun getKwHomeNoticeList(): Call<List<KwHomeNotice>>
}