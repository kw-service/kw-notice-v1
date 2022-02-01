package dev.yjyoon.kwnotice.view.notice.kwhome

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.res.colorResource
import androidx.recyclerview.widget.RecyclerView
import dev.yjyoon.kwnotice.R
import dev.yjyoon.kwnotice.data.remote.model.KwHomeNotice
import dev.yjyoon.kwnotice.databinding.ItemKwHomeNoticeBinding

class KwHomeNoticeListAdapter(val onTap: (KwHomeNotice) -> Unit): RecyclerView.Adapter<KwHomeNoticeListAdapter.ViewHolder>() {

    private var noticeList: List<KwHomeNotice> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemKwHomeNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val favButton = binding.kwHomeFavButton

        favButton.setOnClickListener {
            if(favButton.colorFilter == null) favButton.setColorFilter(Color.parseColor("#FFFFC107"), PorterDuff.Mode.SRC_ATOP)
            else favButton.setColorFilter(null)
        }

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(noticeList[position])
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    inner class ViewHolder(private val binding: ItemKwHomeNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(notice: KwHomeNotice) {
            binding.kwHomeNoticeTitle.text = notice.title
            binding.kwHomeNoticeDate.text = "작성일 ${notice.postedDate} | 수정일 ${notice.modifiedDate}"
            binding.kwHomeNoticeDepartment.text = notice.department

            binding.root.setOnClickListener { onTap(notice) }
        }
    }

    fun setNoticeList(noticeList: List<KwHomeNotice>?) {
        this.noticeList = noticeList?: emptyList()
        notifyDataSetChanged()
    }
}