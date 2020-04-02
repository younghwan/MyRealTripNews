package com.myrealtrip.news.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.myrealtrip.news.R
import com.myrealtrip.news.model.NewsFeedItem
import com.squareup.picasso.Picasso

class NewsItemViewHolder(view: View) : BaseViewHolder(view) {
    private val mPicasso = Picasso.get()
    private val mThumbnail = view.findViewById<ImageView>(R.id.newsThumbnail)
    private val mTitle = view.findViewById<TextView>(R.id.newsTitle)
    private val mDescription = view.findViewById<TextView>(R.id.newsDescription)
    private val mKeywords = view.findViewById<ChipGroup>(R.id.newsKeywords)

    override fun update(item: Any) {
        (item as? NewsFeedItem)?.let { newsFeedItem ->
            newsFeedItem.thumbnail?.let { thumbnail ->
                mPicasso.load(thumbnail).into(mThumbnail)
                mThumbnail.visibility = View.VISIBLE
            } ?: run {
                mThumbnail.visibility = View.INVISIBLE
            }

            mTitle.text = newsFeedItem.title

            mDescription.text = newsFeedItem.description


            mKeywords.run {
                for (i in 0..childCount) {
                    getChildAt(i)?.visibility = View.GONE
                }

                if (newsFeedItem.keywords.isNotEmpty()) {
                    newsFeedItem.keywords.forEachIndexed { index, keyword ->
                        val chip = (getChildAt(index) as? Chip) ?: run {
                            Chip(itemView.context).apply {
                                textSize = 12f
                            }.also {
                                addView(it, index)
                            }
                        }

                        chip.text = keyword
                        chip.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}