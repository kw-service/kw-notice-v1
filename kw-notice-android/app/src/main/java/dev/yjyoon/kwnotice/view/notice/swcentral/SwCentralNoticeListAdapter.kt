package dev.yjyoon.kwnotice.view.notice.swcentral

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.yjyoon.kwnotice.data.remote.model.SwCentralNotice
import dev.yjyoon.kwnotice.databinding.ItemSwCentralNoticeBinding

class SwCentralNoticeListAdapter(val onTap: (SwCentralNotice) -> Unit): RecyclerView.Adapter<SwCentralNoticeListAdapter.ViewHolder>() {
    private var noticeList: List<SwCentralNotice> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSwCentralNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(noticeList[position])
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    inner class ViewHolder(private val binding: ItemSwCentralNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notice: SwCentralNotice) {
            binding.swCentralNoticeTitle.text = notice.title
            binding.swCentralNoticeDate.text = "작성일 ${notice.postedDate}"

            binding.root.setOnClickListener { onTap(notice) }
        }
    }

    fun setNoticeList(noticeList: List<SwCentralNotice>?) {
        this.noticeList = noticeList?: emptyList()
        notifyDataSetChanged()
    }
}