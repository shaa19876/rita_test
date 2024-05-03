package ru.shaa.main.data.api

import okhttp3.Request

class CustomApi {

    private val client = okHttpClient()

    suspend fun getData(url: String) : String? {
        val request = Request.Builder()
            .url(url)
            .build()
        val response = client.newCall(request).execute()

        return response.body?.string()

    }

}