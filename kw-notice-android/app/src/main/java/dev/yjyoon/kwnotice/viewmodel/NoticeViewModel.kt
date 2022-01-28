package dev.yjyoon.kwnotice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.yjyoon.kwnotice.data.remote.model.KwHomeNotice
import dev.yjyoon.kwnotice.repository.NoticeRepository

class NoticeViewModel: ViewModel() {
    private val repository = NoticeRepository()
    private var kwHomeNotices : LiveData<List<KwHomeNotice>> = loadKwHomeNotices()

    fun filterTagKwHomeNotices(tag: String) {
        val filteredNotices = MutableLiveData<List<KwHomeNotice>>()
        filteredNotices.value = kwHomeNotices.value?.filter { notice -> notice.tag == tag }
        kwHomeNotices = filteredNotices
    }

    fun getKwHomeNotices(): LiveData<List<KwHomeNotice>> {
        return kwHomeNotices
    }

    private fun loadKwHomeNotices(): LiveData<List<KwHomeNotice>> {
        return repository.getKwHomeNoticeList()
    }
}