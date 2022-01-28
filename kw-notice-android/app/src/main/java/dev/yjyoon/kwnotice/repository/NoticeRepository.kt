package dev.yjyoon.kwnotice.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.yjyoon.kwnotice.data.remote.api.RetrofitInstance
import dev.yjyoon.kwnotice.data.remote.model.KwHomeNotice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeRepository(application: Application) {
    private val noticeApi = RetrofitInstance.noticeApi

    fun getKwHomeNoticeList(): LiveData<List<KwHomeNotice>> {
        val data = MutableLiveData<List<KwHomeNotice>>()

        noticeApi.getKwHomeNoticeList().enqueue(object : Callback<List<KwHomeNotice>> {
            override fun onResponse(
                call: Call<List<KwHomeNotice>>,
                response: Response<List<KwHomeNotice>>
            ) {
                if(response.isSuccessful)  data.value = response.body()
                else data.value = emptyList()
            }
            override fun onFailure(call: Call<List<KwHomeNotice>>, t: Throwable) {
                t.stackTrace
            }
        })

        return data
    }
}