package dev.yjyoon.kwnotice.viewmodel.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.yjyoon.kwnotice.data.db.dao.FavNoticeDao
import dev.yjyoon.kwnotice.data.db.entity.FavNotice
import kotlinx.coroutines.flow.Flow

class FavNoticeViewModel(private val favNoticeDao: FavNoticeDao): ViewModel() {
    fun favNotices(): Flow<List<FavNotice>> = favNoticeDao.getAllFavNotice()
    suspend fun addFavNotice(notice: FavNotice) = favNoticeDao.addFavNotice(notice)
    suspend fun deleteFavNotice(notice: FavNotice) = favNoticeDao.deleteFavNotice(notice)
}

class FavNoticeViewModelFactory(
    private val favNoticeDao: FavNoticeDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavNoticeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavNoticeViewModel(favNoticeDao) as T
        }
        throw IllegalAccessException("Unknown ViewModel class")
    }
}