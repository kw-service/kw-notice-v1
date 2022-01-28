package dev.yjyoon.kwnotice.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.yjyoon.kwnotice.data.remote.model.KwHomeNotice
import dev.yjyoon.kwnotice.repository.NoticeRepository

class NoticeViewModel(application: Application): AndroidViewModel(application) {
    private val repository = NoticeRepository(application)

    fun getKwHomeNoticeList(): LiveData<List<KwHomeNotice>> {
        return repository.getKwHomeNoticeList()
    }
}