package dev.yjyoon.kwnotice.data.remote.model

import com.google.gson.annotations.SerializedName

data class KwHomeNotice(
        val id: Long,
        val title: String,
        val tag: String,
        @SerializedName("posted_date")  val postedDate: String,
        @SerializedName("modified_date") val modifiedDate: String,
        val department: String,
        val url: String,
        val type: String,
        @SerializedName("crawled_time")  val crawledTime: String
)