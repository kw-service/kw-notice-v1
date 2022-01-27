package dev.yjyoon.kwnotice.view.notice.kwhome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.yjyoon.kwnotice.data.remote.model.KwHomeNotice
import dev.yjyoon.kwnotice.databinding.FragmentKwHomeNoticeBinding

class KwHomeNoticeFragment : Fragment() {

    private val binding by lazy { FragmentKwHomeNoticeBinding.inflate(layoutInflater) }

    private lateinit var noticeList: List<KwHomeNotice>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noticeList = fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val kwHomeNoticeList = binding.kwHomeNoticeList

        kwHomeNoticeList.adapter = KwHomeNoticeListAdapter(noticeList)
        kwHomeNoticeList.layoutManager = LinearLayoutManager(activity)
        kwHomeNoticeList.addItemDecoration(DividerItemDecoration(context, 1))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun fetchData(): MutableList<KwHomeNotice> {
        val data = mutableListOf<KwHomeNotice>()

        for (i in 0..100) {
            val id = i.toLong()
            val title = "[일반] [취업] 2022년 1월 채용 및 취업프로그램 안내(1.27 업데이트)"
            val tag = "일반"
            val postedDate = "2022-01-28"
            val modifiedDate = "2022-01-28"
            val department = "경력개발팀"
            val url = "https://www.google.com"
            val type = "KW_HOME"
            val crawledTime = "2022-01-28 00:00:00"

            data.add(KwHomeNotice(id,title,tag,postedDate,modifiedDate,department,url,type,crawledTime))
        }

        return data
    }
}