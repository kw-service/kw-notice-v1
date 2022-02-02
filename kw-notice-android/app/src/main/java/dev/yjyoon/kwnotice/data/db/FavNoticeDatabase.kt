package dev.yjyoon.kwnotice.data.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.yjyoon.kwnotice.data.db.dao.FavNoticeDao
import dev.yjyoon.kwnotice.data.db.entity.FavNotice

@Database(entities = [FavNotice::class], version = 1, exportSchema = false)
abstract class FavNoticeDatabase: RoomDatabase() {
    abstract fun favNoticeDao(): FavNoticeDao

    companion object {
        @Volatile
        private var INSTANCE: FavNoticeDatabase? = null

        fun getDatabase(context: Context): FavNoticeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    FavNoticeDatabase::class.java,
                    "fav_notice")
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}