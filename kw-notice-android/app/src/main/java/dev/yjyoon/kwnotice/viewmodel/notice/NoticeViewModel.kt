package dev.yjyoon.kwnotice.viewmodel.notice

import androidx.lifecycle.*
import dev.yjyoon.kwnotice.KWNoticeApplication
import dev.yjyoon.kwnotice.data.db.entity.FavNotice
import dev.yjyoon.kwnotice.data.remote.model.KwHomeNotice
import dev.yjyoon.kwnotice.data.remote.model.SwCentralNotice
import dev.yjyoon.kwnotice.repository.favorite.FavRepository
import dev.yjyoon.kwnotice.repository.notice.NoticeRepository
import kotlinx.coroutines.launch

class NoticeViewModel(
    private val noticeRepository: NoticeRepository,
    private val favRepository: FavRepository
) : ViewModel() {

    private lateinit var kwHomeNotices: LiveData<List<KwHomeNotice>>
    private lateinit var swCentralNotices: LiveData<List<SwCentralNotice>>

    val favKwHomeNoticeIds: LiveData<List<Long>> = favRepository.allFavKwHomeNoticeIds.asLiveData()
    val favSwCentralNoticeIds: LiveData<List<Long>> =
        favRepository.allFavSwCentralNoticeIds.asLiveData()

    private var tagFilter = "전체"
    private var departmentFilter = "전체 부서"
    private var sortOption = "최근 수정 순"

    fun filterTagKwHomeNotices(): LiveData<List<KwHomeNotice>> {
        val filteredNotices = MutableLiveData<List<KwHomeNotice>>(kwHomeNotices.value)

        if (tagFilter != "전체") filteredNotices.value =
            filteredNotices.value?.filter { notice -> notice.tag == tagFilter }
        if (departmentFilter != "전체 부서") filteredNotices.value =
            filteredNotices.value?.filter { notice -> notice.department == departmentFilter }
        when (sortOption) {
            "최근 수정 순" -> filteredNotices.value =
                filteredNotices.value?.sortedByDescending { it.modifiedDate }
            "최근 작성 순" -> filteredNotices.value =
                filteredNotices.value?.sortedByDescending { it.postedDate }
        }

        return filteredNotices
    }

    fun loadKwHomeNotices(): LiveData<List<KwHomeNotice>> {
        viewModelScope.launch {
            kwHomeNotices = noticeRepository.getKwHomeNotices()
        }
        return kwHomeNotices
    }

    fun loadSwCentralNotices(): LiveData<List<SwCentralNotice>> {
        viewModelScope.launch {
            swCentralNotices = noticeRepository.getSwCentralNotices()
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

    fun addFavNotice(notice: FavNotice) {
        viewModelScope.launch {
            favRepository.addFavNotice(notice)
        }
    }

    fun deleteFavNotice(noticeId: Long, type: String) {
        viewModelScope.launch {
            favRepository.deleteFavNotice(noticeId, type)
        }
    }
}

class NoticeViewModelFactory(
    private val noticeRepository: NoticeRepository,
    private val favRepository: FavRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoticeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoticeViewModel(noticeRepository, favRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}