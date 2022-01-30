package dev.yjyoon.kwnotice.view.notice.swcentral

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.yjyoon.kwnotice.data.remote.model.SwCentralNotice
import dev.yjyoon.kwnotice.databinding.FragmentSwCentralNoticeBinding
import dev.yjyoon.kwnotice.view.notice.webview.WebViewActivity
import dev.yjyoon.kwnotice.viewmodel.NoticeViewModel


class SwCentralNoticeFragment : Fragment() {

    private val binding by lazy { FragmentSwCentralNoticeBinding.inflate(layoutInflater) }
    private val viewModel: NoticeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val listAdapter = SwCentralNoticeListAdapter { notice: SwCentralNotice ->
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra("url", notice.url)
            startActivity(intent)
        }

        initRecyclerView()

        viewModel.loadSwCentralNotices().observe(viewLifecycleOwner, { notices ->
            binding.swCentralProgressBar.visibility = View.VISIBLE

            initAdapter(listAdapter, notices)

            binding.swCentralProgressBar.visibility = View.INVISIBLE
        })

        return binding.root
    }

    private fun initRecyclerView() {
        binding.swCentralNoticeList.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.setHasFixedSize(true)
            this.addItemDecoration(DividerItemDecoration(context, 1))
        }
    }

    private fun initAdapter(adapter: SwCentralNoticeListAdapter, notices: List<SwCentralNotice>) {
        adapter.setNoticeList(notices)
        binding.swCentralNoticeList.adapter = adapter
    }

}