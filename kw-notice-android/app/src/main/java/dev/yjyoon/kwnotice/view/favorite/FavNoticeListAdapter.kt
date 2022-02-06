package dev.yjyoon.kwnotice.view.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.yjyoon.kwnotice.data.db.entity.FavNotice
import dev.yjyoon.kwnotice.databinding.ItemFavNoticeBinding

class FavNoticeListAdapter(
    private val onItemClicked: (FavNotice) -> Unit,
    private val deleteFav: (Long, String) -> Unit
) : ListAdapter<FavNotice, FavNoticeListAdapter.FavNoticeViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavNoticeViewHolder {
        val viewHolder = FavNoticeViewHolder(
            ItemFavNoticeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: FavNoticeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavNoticeViewHolder(private val binding: ItemFavNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favNotice: FavNotice) {
            binding.favNoticeTitle.text = favNotice.title
            binding.favNoticeType.text = when(favNotice.type) {
                "KW_HOME" -> "광운대학교"
                "SW_CENTRAL" -> "SW중심대학사업단"
                else -> "UNKNOWN"
            }
            binding.root.setOnClickListener { onItemClicked(favNotice) }

            binding.favButton.setOnClickListener{
                deleteFav(favNotice.noticeId, favNotice.type)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<FavNotice>() {
            override fun areItemsTheSame(oldItem: FavNotice, newItem: FavNotice): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavNotice, newItem: FavNotice): Boolean {
                return oldItem == newItem
            }

        }
    }

}