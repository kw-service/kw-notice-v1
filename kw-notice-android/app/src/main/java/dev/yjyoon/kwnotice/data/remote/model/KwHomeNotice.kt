package dev.yjyoon.kwnotice.data.remote.model

data class KwHomeNotice(
        val id: Long,
        val title: String,
        val tag: String,
        val postedDate: String,
        val modifiedDate: String,
        val department: String,
        val url: String,
        val type: String,
        val crawledTime: String
)