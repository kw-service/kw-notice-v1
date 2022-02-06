package dev.yjyoon.kwnotice.repository.favorite

import androidx.annotation.WorkerThread
import dev.yjyoon.kwnotice.data.db.dao.FavNoticeDao
import dev.yjyoon.kwnotice.data.db.entity.FavNotice
import kotlinx.coroutines.flow.Flow

class FavRepository(private val favNoticeDao: FavNoticeDao) {

    val allFavKwHomeNoticeIds: Flow<List<Long>> = favNoticeDao.getFavKwHomeNoticeIds()
    val allFavSwCentralNoticeIds: Flow<List<Long>> = favNoticeDao.getFavSwCentralNoticeIds()

    @WorkerThread
    suspend fun addFavNotice(notice: FavNotice) {
        favNoticeDao.addFavNotice(notice)
    }

    @WorkerThread
    suspend fun deleteFavNotice(noticeId: Long, type: String) {
        favNoticeDao.deleteFavNotice(noticeId, type)
    }

    @WorkerThread
    suspend fun isFavNotice(noticeId: Long, type: String): Long? {
        return favNoticeDao.isFavNotice(noticeId, type)
    }
}