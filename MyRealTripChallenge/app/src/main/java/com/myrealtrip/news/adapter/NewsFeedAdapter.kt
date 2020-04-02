package com.myrealtrip.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myrealtrip.news.R
import com.myrealtrip.news.model.NewsFeedItem
import com.myrealtrip.news.viewholder.BaseViewHolder
import com.myrealtrip.news.viewholder.DefaultViewHolder
import com.myrealtrip.news.viewholder.NewsItemViewHolder


class NewsFeedAdapter(private var itemClickListener: ItemClickLister) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private var mItems = mutableListOf<Any>()

    private enum class ViewType(var viewType: Int) {
        NEWS(0), DEFAULT(1)
    }

    init {
        setHasStableIds(true)
    }

    interface ItemClickLister {
        fun onItemClicked(item: NewsFeedItem)
    }

    fun addItem(item: Any) {
        synchronized(mItems) {
            mItems.add(item)
            notifyDataSetChanged()
        }
    }

    fun clearItems() {
        synchronized(mItems) {
            mItems.clear()
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (mItems[position]) {
            is NewsFeedItem -> ViewType.NEWS.viewType
            else -> ViewType.DEFAULT.viewType
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            when (viewType) {
                ViewType.NEWS.viewType -> R.layout.row_news_item
                else -> R.layout.row_default
            },
            parent,
            false
        )

        return when (viewType) {
            ViewType.NEWS.viewType -> NewsItemViewHolder(
                view
            )
            else -> DefaultViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun getItemId(position: Int): Long {
        return mItems[position].hashCode().toLong()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = mItems[position]
        holder.update(item)

        holder.itemView.setOnClickListener{
            (item as? NewsFeedItem)?.let {
                itemClickListener.onItemClicked(it)
            }
        }
    }
}