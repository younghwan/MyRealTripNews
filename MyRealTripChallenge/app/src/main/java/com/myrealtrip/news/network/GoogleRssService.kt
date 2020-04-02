package com.myrealtrip.news.network

import com.myrealtrip.news.model.Rss
import retrofit2.http.GET

interface GoogleRssService {
    @GET("/rss?hl=ko&gl=KR&ceid=KR:ko")
    suspend fun getRss(): Rss
}