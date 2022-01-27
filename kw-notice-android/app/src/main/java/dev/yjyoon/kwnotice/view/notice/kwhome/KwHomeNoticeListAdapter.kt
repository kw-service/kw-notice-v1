package dev.yjyoon.kwnotice.view.notice.kwhome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.yjyoon.kwnotice.data.remote.model.KwHomeNotice
import dev.yjyoon.kwnotice.databinding.ItemKwHomeNoticeBinding

class KwHomeNoticeListAdapter(private val noticeList: List<KwHomeNotice>): RecyclerView.Adapter<KwHomeNoticeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKwHomeNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setNotice(noticeList[position])
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    inner class ViewHolder(private val binding: ItemKwHomeNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setNotice(notice: KwHomeNotice) {
            binding.kwHomeNoticeTitle.text = notice.title
            binding.kwHomeNoticeDate.text = "작성일 ${notice.postedDate} | 수정일 ${notice.modifiedDate}"
            binding.kwHomeNoticeDepartment.text = notice.department
        }
    }

}