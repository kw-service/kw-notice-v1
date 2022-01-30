package dev.yjyoon.kwnotice.data.remote.model

import com.google.gson.annotations.SerializedName

data class SwCentralNotice(
    val id: Long,
    val title: String,
    @SerializedName("posted_date")  val postedDate: String,
    val url: String,
    val type: String,
    @SerializedName("crawled_time")  val crawledTime: String
)
