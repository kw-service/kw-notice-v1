package dev.yjyoon.kwnotice

import android.app.Application
import dev.yjyoon.kwnotice.data.db.FavNoticeDatabase

class KWNoticeApplication : Application() {
    val favNoticeDatabase : FavNoticeDatabase by lazy { FavNoticeDatabase.getDatabase(this) }
}