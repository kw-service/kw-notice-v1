package dev.yjyoon.kwnotice.data.remote.api

import dev.yjyoon.kwnotice.data.remote.model.KwHomeNotice
import dev.yjyoon.kwnotice.data.remote.model.SwCentralNotice
import retrofit2.Call
import retrofit2.http.GET

interface NoticeService {
    @GET("kw-home")
    fun getKwHomeNotices(): Call<List<KwHomeNotice>>

    @GET("sw-central")
    fun getSwCentralNotices(): Call<List<SwCentralNotice>>
}