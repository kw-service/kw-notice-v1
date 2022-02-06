package dev.yjyoon.kwnotice.repository.notice

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.yjyoon.kwnotice.data.remote.api.RetrofitInstance
import dev.yjyoon.kwnotice.data.remote.model.KwHomeNotice
import dev.yjyoon.kwnotice.data.remote.model.SwCentralNotice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeRepository {
    private val noticeApi = RetrofitInstance.noticeApi

    fun getKwHomeNotices(): LiveData<List<KwHomeNotice>> {
        val data = MutableLiveData<List<KwHomeNotice>>()

        noticeApi.getKwHomeNotices().enqueue(object : Callback<List<KwHomeNotice>> {
            override fun onResponse(
                call: Call<List<KwHomeNotice>>,
                response: Response<List<KwHomeNotice>>
            ) {
                if(response.isSuccessful) data.value = response.body()
                else data.value = emptyList()
            }
            override fun onFailure(call: Call<List<KwHomeNotice>>, t: Throwable) {
                t.stackTrace
            }
        })

        return data
    }

    fun getSwCentralNotices(): LiveData<List<SwCentralNotice>> {
        val data = MutableLiveData<List<SwCentralNotice>>()

        noticeApi.getSwCentralNotices().enqueue(object : Callback<List<SwCentralNotice>> {
            override fun onResponse(
                call: Call<List<SwCentralNotice>>,
                response: Response<List<SwCentralNotice>>
            ) {
                if(response.isSuccessful) data.value = response.body()
                else data.value = emptyList()
            }
            override fun onFailure(call: Call<List<SwCentralNotice>>, t: Throwable) {
                t.stackTrace
            }
        })

        return data
    }
}