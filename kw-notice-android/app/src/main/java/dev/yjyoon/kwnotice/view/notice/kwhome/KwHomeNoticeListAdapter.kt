package dev.yjyoon.kwnotice.view.notice.kwhome

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.ui.res.colorResource
import androidx.recyclerview.widget.RecyclerView
import dev.yjyoon.kwnotice.R
import dev.yjyoon.kwnotice.data.db.FavNoticeDatabase
import dev.yjyoon.kwnotice.data.db.entity.FavNotice
import dev.yjyoon.kwnotice.data.remote.model.KwHomeNotice
import dev.yjyoon.kwnotice.databinding.ItemKwHomeNoticeBinding
import kotlinx.coroutines.*
import java.time.LocalDateTime
import kotlin.math.acos

class KwHomeNoticeListAdapter(
    val onTap: (KwHomeNotice) -> Unit,
    val addFav: (FavNotice) -> Unit,
    val deleteFav: (Long, String) -> Unit
) : RecyclerView.Adapter<KwHomeNoticeListAdapter.ViewHolder>() {

    private var noticeList: List<KwHomeNotice> = emptyList()
    private var favIdList: List<Long> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemKwHomeNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(noticeList[position])
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    inner class ViewHolder(private val binding: ItemKwHomeNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(notice: KwHomeNotice) {
            binding.kwHomeNoticeTitle.text = notice.title
            binding.kwHomeNoticeDate.text = "작성일 ${notice.postedDate} | 수정일 ${notice.modifiedDate}"
            binding.kwHomeNoticeDepartment.text = notice.department

            binding.root.setOnClickListener { onTap(notice) }

            val favButton = binding.kwHomeFavButton

            if(notice.id in favIdList) favButton.setColorFilter(Color.parseColor("#FFFFC107"),
                PorterDuff.Mode.SRC_ATOP)
            else favButton.colorFilter = null

            favButton.setOnClickListener {
                if (favButton.colorFilter == null) {
                    val favNotice = FavNotice(
                        id = null,
                        noticeId = notice.id,
                        title = notice.title,
                        type = notice.type,
                        url = notice.url
                    )

                    addFav(favNotice)

                    favButton.setColorFilter(
                        Color.parseColor("#FFFFC107"),
                        PorterDuff.Mode.SRC_ATOP
                    )
                } else {
                    deleteFav(notice.id, notice.type)

                    favButton.colorFilter = null
                }
            }
        }
    }

    fun setNoticeList(noticeList: List<KwHomeNotice>?) {
        this.noticeList = noticeList ?: emptyList()
        notifyDataSetChanged()
    }

    fun setFavIdList(favIdList: List<Long>) {
        this.favIdList = favIdList
        notifyDataSetChanged()
    }

}