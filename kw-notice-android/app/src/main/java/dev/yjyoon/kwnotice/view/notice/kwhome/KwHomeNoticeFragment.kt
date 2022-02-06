package dev.yjyoon.kwnotice.view.notice.kwhome

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.yjyoon.kwnotice.KWNoticeApplication
import dev.yjyoon.kwnotice.R
import dev.yjyoon.kwnotice.data.db.entity.FavNotice
import dev.yjyoon.kwnotice.data.remote.model.KwHomeNotice
import dev.yjyoon.kwnotice.databinding.FragmentKwHomeNoticeBinding
import dev.yjyoon.kwnotice.view.notice.webview.WebViewActivity
import dev.yjyoon.kwnotice.viewmodel.notice.NoticeViewModel
import dev.yjyoon.kwnotice.viewmodel.notice.NoticeViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext


class KwHomeNoticeFragment : Fragment() {

    private val binding by lazy { FragmentKwHomeNoticeBinding.inflate(layoutInflater) }
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

        val showNotice: (KwHomeNotice) -> Unit = { notice: KwHomeNotice ->
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

        val listAdapter = KwHomeNoticeListAdapter(
            showNotice,
            addFav,
            deleteFav,
        )

        initRecyclerView(listAdapter)

        viewModel.loadKwHomeNotices().observe(viewLifecycleOwner, { notices ->
            binding.kwHomeProgressBar.visibility = View.VISIBLE
            initAdapter(listAdapter, notices)
            binding.kwHomeProgressBar.visibility = View.INVISIBLE

            binding.cannotLoadKwHome.visibility = if(notices.isEmpty()) View.VISIBLE else View.INVISIBLE
        })

        viewModel.favKwHomeNoticeIds.observe(viewLifecycleOwner, { ids ->
            listAdapter.setFavIdList(ids)
        })

        initListener(listAdapter)

        return binding.root
    }

    private fun initRecyclerView(adapter: KwHomeNoticeListAdapter) {
        binding.kwHomeNoticeList.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.setHasFixedSize(true)
            this.addItemDecoration(DividerItemDecoration(context, 1))
            this.adapter = adapter
        }
    }

    private fun initAdapter(adapter: KwHomeNoticeListAdapter, notices: List<KwHomeNotice>) {

        adapter.setNoticeList(notices)

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

        binding.departmentSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
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