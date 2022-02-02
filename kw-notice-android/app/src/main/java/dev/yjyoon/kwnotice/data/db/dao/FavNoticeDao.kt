package dev.yjyoon.kwnotice.data.db.dao

import androidx.room.*
import dev.yjyoon.kwnotice.data.db.entity.FavNotice
import kotlinx.coroutines.flow.Flow

@Dao
interface FavNoticeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavNotice(notice: FavNotice)

    @Delete
    suspend fun deleteFavNotice(notice: FavNotice)

    @Query("SELECT * from fav_notice ORDER BY id DESC")
    fun getAllFavNotice(): Flow<List<FavNotice>>
}