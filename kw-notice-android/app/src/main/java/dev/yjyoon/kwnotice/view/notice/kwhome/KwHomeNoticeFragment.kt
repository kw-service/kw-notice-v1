package dev.yjyoon.kwnotice.view.notice.kwhome

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.yjyoon.kwnotice.data.remote.model.KwHomeNotice
import dev.yjyoon.kwnotice.databinding.FragmentKwHomeNoticeBinding
import dev.yjyoon.kwnotice.view.notice.webview.WebViewActivity
import dev.yjyoon.kwnotice.viewmodel.NoticeViewModel


class KwHomeNoticeFragment : Fragment() {

    private val binding by lazy { FragmentKwHomeNoticeBinding.inflate(layoutInflater) }
    private lateinit var viewModel: NoticeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(this).get(NoticeViewModel::class.java)

        val adapter = KwHomeNoticeListAdapter { notice: KwHomeNotice ->
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra("url", notice.url)
            startActivity(intent)
        }

        initRecyclerView(adapter)

        return binding.root
    }

    private fun initRecyclerView(adapter: KwHomeNoticeListAdapter) {
        binding.kwHomeNoticeList.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(activity)
            this.setHasFixedSize(true)
            this.addItemDecoration(DividerItemDecoration(context, 1))
        }

        viewModel.getKwHomeNoticeList().observe(viewLifecycleOwner, { noticeList -> adapter.setNoticeList(noticeList) })
    }
}