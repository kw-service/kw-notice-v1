package dev.yjyoon.kwnotice.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "fav_notice")
data class FavNotice(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    val noticeId: Long,
    val title: String,
    val type: String,
    val url: String,
    val createdTime: Long = System.currentTimeMillis()
)
