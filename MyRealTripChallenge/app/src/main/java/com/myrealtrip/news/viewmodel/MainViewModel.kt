package com.myrealtrip.news.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.myrealtrip.news.model.NewsFeedItem
import com.myrealtrip.news.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup


class MainViewModel(application: Application) : AndroidViewModel(application) {
    val newsFeedData = MutableLiveData<NewsFeedItem>()
    val loading = MutableLiveData<Boolean>()

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun loadNewsFeed() {
        if (loading.value == true) {
            return
        }

        viewModelScope.launch {
            loading.value = true

            withContext(Dispatchers.IO) {
                val rss = ApiService.googleRssService.getRss()

                rss.channel?.item?.forEach {
                    try {
                        newsFeedData.postValue(getNewsFeedItem(it.title, it.link))
                    } catch (e: Exception) {
                        Log.e(TAG, e.message ?: "")
                    }
                }
            }

            loading.value = false
        }
    }

    @Throws(Exception::class)
    private fun getNewsFeedItem(title: String, url: String): NewsFeedItem {
        val doc = Jsoup.connect(url).get()
        val description = doc.getElementsByAttributeValue(
            "property",
            "og:description"
        ).firstOrNull()?.attr("content")?.trim() ?: ""

        val imageUrl = doc.getElementsByAttributeValue(
            "property",
            "og:image"
        ).firstOrNull()?.attr("content")?.trim()

        val newsFeedItem = NewsFeedItem(title, description, imageUrl, getTop3Keywords(description))
        newsFeedItem.link = url
        return newsFeedItem
    }

    private fun getTop3Keywords(description: String): List<String> {
        val words = reOrganizeWord(description)

        val map: MutableMap<String, Int> = HashMap()

        words.filter { it.length > 1 }.forEach {
            map[it] = (map[it] ?: 0) + 1
        }

        return map.keys.sortedByDescending { map[it] }.take(3)
    }

    private fun reOrganizeWord(word:String):List<String>{
        return word.replace(",", "").replace(".", "").replace("\"","")
        .split(" ")
    }
}