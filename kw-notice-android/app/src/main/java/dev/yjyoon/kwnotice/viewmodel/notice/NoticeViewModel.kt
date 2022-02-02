package dev.yjyoon.kwnotice.viewmodel.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yjyoon.kwnotice.data.remote.model.KwHomeNotice
import dev.yjyoon.kwnotice.data.remote.model.SwCentralNotice
import dev.yjyoon.kwnotice.repository.NoticeRepository
import kotlinx.coroutines.launch

class NoticeViewModel: ViewModel() {
    private val repository = NoticeRepository()

    private lateinit var kwHomeNotices : LiveData<List<KwHomeNotice>>
    private lateinit var swCentralNotices : LiveData<List<SwCentralNotice>>

    private var tagFilter = "전체"
    private var departmentFilter = "전체 부서"
    private var sortOption = "최근 수정 순"

    fun filterTagKwHomeNotices(): LiveData<List<KwHomeNotice>> {
        val filteredNotices = MutableLiveData<List<KwHomeNotice>>(kwHomeNotices.value)

        if(tagFilter != "전체")  filteredNotices.value = filteredNotices.value?.filter { notice -> notice.tag == tagFilter }
        if(departmentFilter != "전체 부서") filteredNotices.value = filteredNotices.value?.filter { notice -> notice.department == departmentFilter }
        when(sortOption) {
            "최근 수정 순" -> filteredNotices.value = filteredNotices.value?.sortedByDescending{ it.modifiedDate }
            "최근 작성 순" -> filteredNotices.value = filteredNotices.value?.sortedByDescending{ it.postedDate }
        }

        return filteredNotices
    }

   fun loadKwHomeNotices(): LiveData<List<KwHomeNotice>> {
        viewModelScope.launch {
            kwHomeNotices = repository.getKwHomeNotices()
        }
        return kwHomeNotices
    }

    fun loadSwCentralNotices(): LiveData<List<SwCentralNotice>> {
        viewModelScope.launch {
            swCentralNotices = repository.getSwCentralNotices()
        }
        return swCentralNotices
    }

    fun setTagFilter(tag: String) {
        this.tagFilter = tag
    }

    fun setDepartmentFilter(department: String) {
        this.departmentFilter = department
    }
    fun setSortOption(sort: String) {
        this.sortOption = sort
    }

}