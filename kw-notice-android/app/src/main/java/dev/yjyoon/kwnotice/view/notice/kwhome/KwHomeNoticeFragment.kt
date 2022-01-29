package dev.yjyoon.kwnotice.view.notice.kwhome

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.yjyoon.kwnotice.R
import dev.yjyoon.kwnotice.data.remote.model.KwHomeNotice
import dev.yjyoon.kwnotice.databinding.FragmentKwHomeNoticeBinding
import dev.yjyoon.kwnotice.view.notice.webview.WebViewActivity
import dev.yjyoon.kwnotice.viewmodel.NoticeViewModel


class KwHomeNoticeFragment : Fragment() {

    private val binding by lazy { FragmentKwHomeNoticeBinding.inflate(layoutInflater) }
    val viewModel: NoticeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val listAdapter = KwHomeNoticeListAdapter { notice: KwHomeNotice ->
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra("url", notice.url)
            startActivity(intent)
        }

        initRecyclerView()

        viewModel.loadKwHomeNotices().observe(viewLifecycleOwner, { notices ->
            initAdapter(listAdapter, notices)
        })

        initListener(listAdapter)

        return binding.root
    }

    private fun initRecyclerView() {
        binding.kwHomeNoticeList.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.setHasFixedSize(true)
            this.addItemDecoration(DividerItemDecoration(context, 1))
        }
    }

    private fun initAdapter(adapter: KwHomeNoticeListAdapter, notices: List<KwHomeNotice>) {
        adapter.setNoticeList(notices)
        binding.kwHomeNoticeList.adapter = adapter

        val tagSpinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner,
            listOf("전체") + notices.map { notice -> notice.tag }.distinct()
        )

        binding.tagSpinner.adapter = tagSpinnerAdapter

        val departmentSpinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner,
            listOf("전체 부서") + notices.map { notice -> notice.department }.distinct()
        )

        binding.departmentSpinner.adapter = departmentSpinnerAdapter

        binding.sortSpinner.adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner,
            listOf("최근 수정 순", "최근 작성 순")
        )
    }

    private fun initListener(adapter: KwHomeNoticeListAdapter) {
        binding.tagSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.setTagFilter(binding.tagSpinner.selectedItem.toString())
                viewModel.filterTagKwHomeNotices().observe(viewLifecycleOwner, { notices ->
                    adapter.setNoticeList(notices)
                })
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.departmentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.setDepartmentFilter(binding.departmentSpinner.selectedItem.toString())
                viewModel.filterTagKwHomeNotices().observe(viewLifecycleOwner, { notices ->
                    adapter.setNoticeList(notices)
                })
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.setSortOption(binding.sortSpinner.selectedItem.toString())
                viewModel.filterTagKwHomeNotices().observe(viewLifecycleOwner, { notices ->
                    adapter.setNoticeList(notices)
                })
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

}