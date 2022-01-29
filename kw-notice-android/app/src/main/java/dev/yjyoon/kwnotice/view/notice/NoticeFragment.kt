package dev.yjyoon.kwnotice.view.notice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import dev.yjyoon.kwnotice.databinding.FragmentNoticeBinding
import dev.yjyoon.kwnotice.view.notice.kwhome.KwHomeNoticeFragment
import dev.yjyoon.kwnotice.view.notice.swcentral.SwCentralNoticeFragment

class NoticeFragment : Fragment() {

    private val binding by lazy { FragmentNoticeBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val fragmentList = listOf(KwHomeNoticeFragment(), SwCentralNoticeFragment())

        // Fragment 안에서 ViewPager 사용 시 FragmentStateAdapter 에 childFragmentManager 전달 필요
        binding.noticeViewPager.adapter = NoticeFragmentAdapter(childFragmentManager, lifecycle, fragmentList)

        val tabTitles = listOf("광운대학교", "SW중심대학사업단")
        TabLayoutMediator(
            binding.noticeTabLayout,
            binding.noticeViewPager
        ) { tab, position -> tab.text = tabTitles[position] }.attach()

        return binding.root
    }
}