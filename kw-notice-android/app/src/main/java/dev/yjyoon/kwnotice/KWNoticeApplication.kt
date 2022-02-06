package dev.yjyoon.kwnotice

import android.app.Application
import dev.yjyoon.kwnotice.data.db.FavNoticeDatabase
import dev.yjyoon.kwnotice.repository.favorite.FavRepository
import dev.yjyoon.kwnotice.repository.notice.NoticeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class KWNoticeApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val favNoticeDatabase by lazy { FavNoticeDatabase.getDatabase(this, applicationScope) }
    val favRepository by lazy { FavRepository(favNoticeDatabase.favNoticeDao()) }
    val noticeRepository by lazy { NoticeRepository() }
}