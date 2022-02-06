package dev.yjyoon.kwnotice.view.notice.swcentral

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.yjyoon.kwnotice.data.db.entity.FavNotice
import dev.yjyoon.kwnotice.data.remote.model.SwCentralNotice
import dev.yjyoon.kwnotice.databinding.ItemSwCentralNoticeBinding

class SwCentralNoticeListAdapter(
    val onTap: (SwCentralNotice) -> Unit,
    val addFav: (FavNotice) -> Unit,
    val deleteFav: (Long, String) -> Unit
) : RecyclerView.Adapter<SwCentralNoticeListAdapter.ViewHolder>() {

    private var noticeList: List<SwCentralNotice> = emptyList()
    private var favIdList: List<Long> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemSwCentralNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(noticeList[position])
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    inner class ViewHolder(private val binding: ItemSwCentralNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(notice: SwCentralNotice) {
            binding.swCentralNoticeTitle.text = notice.title
            binding.swCentralNoticeDate.text = "작성일 ${notice.postedDate}"

            binding.root.setOnClickListener { onTap(notice) }

            val favButton = binding.swCentralFavButton

            if(notice.id in favIdList) favButton.setColorFilter(
                Color.parseColor("#FFFFC107"),
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

    fun setNoticeList(noticeList: List<SwCentralNotice>?) {
        this.noticeList = noticeList ?: emptyList()
        notifyDataSetChanged()
    }

    fun setFavIdList(favIdList: List<Long>) {
        this.favIdList = favIdList
        notifyDataSetChanged()
    }
}