package com.myrealtrip.news.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

//singleton
object ApiService {
    private var mGoogleRssService: GoogleRssService? = null

    val googleRssService: GoogleRssService
        get() = mGoogleRssService ?: run {
            Retrofit.Builder()
                .baseUrl("https://news.google.com")
                .client(OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build().create(GoogleRssService::class.java).also {
                    mGoogleRssService = it
                }
        }
}