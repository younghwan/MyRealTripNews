package com.myrealtrip.news.model

data class NewsFeedItem(
    var title: String = "",
    var description: String = "",
    var thumbnail: String? = null,
    var keywords: List<String> = emptyList()
)
{
    var link = ""
}
