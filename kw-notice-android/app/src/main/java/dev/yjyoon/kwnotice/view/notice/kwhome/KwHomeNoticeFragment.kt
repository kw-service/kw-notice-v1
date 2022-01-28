package dev.yjyoon.kwnotice.view.notice.kwhome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.yjyoon.kwnotice.databinding.FragmentKwHomeNoticeBinding
import dev.yjyoon.kwnotice.viewmodel.NoticeViewModel


class KwHomeNoticeFragment : Fragment() {

    private val binding by lazy { FragmentKwHomeNoticeBinding.inflate(layoutInflater) }
    private lateinit var viewModel: NoticeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = KwHomeNoticeListAdapter({ notice -> })

        initRecyclerView(adapter)
        initViewModel(adapter)

        return binding.root
    }

    private fun initRecyclerView(adapter: KwHomeNoticeListAdapter) {
        binding.kwHomeNoticeList.adapter = adapter
        binding.kwHomeNoticeList.layoutManager = LinearLayoutManager(activity)
        binding.kwHomeNoticeList.setHasFixedSize(true)
        binding.kwHomeNoticeList.addItemDecoration(DividerItemDecoration(context, 1))
    }

    private fun initViewModel(adapter: KwHomeNoticeListAdapter) {
        viewModel = ViewModelProvider(this).get(NoticeViewModel::class.java)
        viewModel.getKwHomeNoticeList().observe(viewLifecycleOwner, { noticeList -> adapter.setNoticeList(noticeList) })
    }
}