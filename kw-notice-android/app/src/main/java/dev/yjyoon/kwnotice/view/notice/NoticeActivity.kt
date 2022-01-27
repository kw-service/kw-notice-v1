package dev.yjyoon.kwnotice.view.notice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import dev.yjyoon.kwnotice.databinding.ActivityNoticeBinding
import dev.yjyoon.kwnotice.view.notice.kwhome.KwHomeNoticeFragment

class NoticeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityNoticeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val fragmentList = listOf<Fragment>(KwHomeNoticeFragment(), SwCentralNoticeFragment())

        binding.noticeViewPager.adapter = NoticeFragmentAdapter(this, fragmentList)

        val tabTitles = listOf<String>("광운대학교", "SW중심대학사업단")
        TabLayoutMediator(binding.noticeTabLayout, binding.noticeViewPager) { tab, position -> tab.text = tabTitles[position] }.attach()
    }
}