package dev.yjyoon.kwnotice.view.notice.swcentral

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.yjyoon.kwnotice.KWNoticeApplication
import dev.yjyoon.kwnotice.R
import dev.yjyoon.kwnotice.data.db.entity.FavNotice
import dev.yjyoon.kwnotice.data.remote.model.KwHomeNotice
import dev.yjyoon.kwnotice.data.remote.model.SwCentralNotice
import dev.yjyoon.kwnotice.databinding.FragmentSwCentralNoticeBinding
import dev.yjyoon.kwnotice.view.notice.kwhome.KwHomeNoticeListAdapter
import dev.yjyoon.kwnotice.view.notice.webview.WebViewActivity
import dev.yjyoon.kwnotice.viewmodel.notice.NoticeViewModel
import dev.yjyoon.kwnotice.viewmodel.notice.NoticeViewModelFactory


class SwCentralNoticeFragment : Fragment() {

    private val binding by lazy { FragmentSwCentralNoticeBinding.inflate(layoutInflater) }
    private val viewModel: NoticeViewModel by viewModels() {
        val kwNoticeApplication = activity?.application as KWNoticeApplication
        NoticeViewModelFactory(
            kwNoticeApplication.noticeRepository,
            kwNoticeApplication.favRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val showNotice: (SwCentralNotice) -> Unit = { notice: SwCentralNotice ->
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra("url", notice.url)
            startActivity(intent)
        }

        val addFav: (FavNotice) -> Unit = { notice: FavNotice ->
            viewModel.addFavNotice(notice)
        }

        val deleteFav: (Long, String) -> Unit = { noticeId: Long, type: String ->
            viewModel.deleteFavNotice(noticeId, type)
        }

        val listAdapter = SwCentralNoticeListAdapter(
            showNotice,
            addFav,
            deleteFav,
        )

        initRecyclerView(listAdapter)

        viewModel.loadSwCentralNotices().observe(viewLifecycleOwner, { notices ->
            binding.swCentralProgressBar.visibility = View.VISIBLE
            listAdapter.setNoticeList(notices)
            binding.swCentralProgressBar.visibility = View.INVISIBLE

            binding.cannotLoadSwCentral.visibility = if(notices.isEmpty()) View.VISIBLE else View.INVISIBLE
        })

        viewModel.favSwCentralNoticeIds.observe(viewLifecycleOwner, { ids ->
            listAdapter.setFavIdList(ids)
        })

        initSpinner()

        return binding.root
    }

    private fun initRecyclerView(adapter: SwCentralNoticeListAdapter) {
        binding.swCentralNoticeList.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    private fun initSpinner() {
        val tagSpinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner,
            listOf("전체")
        )

        binding.tagSpinner2.adapter = tagSpinnerAdapter

        val departmentSpinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner,
            listOf("전체 부서")
        )

        binding.departmentSpinner2.adapter = departmentSpinnerAdapter

        binding.sortSpinner2.adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner,
            listOf("최근 작성 순")
        )
    }
}