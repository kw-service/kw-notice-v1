package dev.yjyoon.kwnotice.data.db.dao

import androidx.room.*
import dev.yjyoon.kwnotice.data.db.entity.FavNotice
import kotlinx.coroutines.flow.Flow

@Dao
interface FavNoticeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavNotice(notice: FavNotice)

    @Query("DELETE FROM fav_notice WHERE noticeId = :noticeId AND type = :type")
    suspend fun deleteFavNotice(noticeId: Long, type: String)

    @Query("SELECT * FROM fav_notice ORDER BY id DESC")
    fun getAllFavNotice(): Flow<List<FavNotice>>

    @Query("SELECT noticeId FROM fav_notice WHERE type = 'KW_HOME' ORDER BY id DESC")
    fun getFavKwHomeNoticeIds(): Flow<List<Long>>

    @Query("SELECT noticeId FROM fav_notice WHERE type = 'SW_CENTRAL' ORDER BY id DESC")
    fun getFavSwCentralNoticeIds(): Flow<List<Long>>

    @Query("SELECT id FROM fav_notice WHERE noticeId = :noticeId AND type = :type")
    suspend fun isFavNotice(noticeId: Long, type: String): Long?
}