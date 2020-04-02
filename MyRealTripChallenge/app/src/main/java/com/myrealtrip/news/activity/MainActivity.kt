package com.myrealtrip.news.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.myrealtrip.news.viewmodel.MainViewModel
import com.myrealtrip.news.R
import com.myrealtrip.news.adapter.NewsFeedAdapter
import com.myrealtrip.news.model.NewsFeedItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val mMainViewModel: MainViewModel by viewModels()
    private val mNewsFeedAdapter =
        NewsFeedAdapter(object : NewsFeedAdapter.ItemClickLister{
            override fun onItemClicked(item: NewsFeedItem) {
                val nextItent = Intent(this@MainActivity,DetailActivity::class.java)
                nextItent.putExtra("url",item.link)
                startActivity(nextItent)
            }
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initViewModel()
        loadNewsFeed()
    }

    private fun initViews() {
        newsFeedList.adapter = mNewsFeedAdapter

        refreshLayout.setOnRefreshListener {
            loadNewsFeed()
        }
    }

    private fun initViewModel() {
        mMainViewModel.run {
            newsFeedData.observe(this@MainActivity, Observer { newsFeed ->
                mNewsFeedAdapter.addItem(newsFeed)
            })

            loading.observe(this@MainActivity, Observer {
                refreshLayout.isRefreshing = it
            })
        }
    }

    private fun loadNewsFeed() {
        mNewsFeedAdapter.clearItems()
        mMainViewModel.loadNewsFeed()
    }
}