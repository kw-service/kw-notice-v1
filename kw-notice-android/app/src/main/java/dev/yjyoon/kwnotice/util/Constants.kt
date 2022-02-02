package dev.yjyoon.kwnotice.util

class Constants {
    companion object {
        const val API_BASE_URL = "https://nl3mk0r96i.execute-api.ap-northeast-2.amazonaws.com/v1/"
        val FCM_TOPICS = listOf("kw-home-new", "kw-home-edit", "sw-central-new")
    }
}