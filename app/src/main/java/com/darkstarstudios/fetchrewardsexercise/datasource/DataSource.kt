package com.darkstarstudios.fetchrewardsexercise.datasource

import android.util.Log
import com.darkstarstudios.fetchrewardsexercise.model.Reward
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


class DataSource() {

    fun fetchRewards(url: String, onResult: (List<Reward>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val client = OkHttpClient()

                val request = Request.Builder().url(url).build()
                val response: Response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val jsonResponse = response.body?.string()
                    jsonResponse?.let {
                        val json = Json { ignoreUnknownKeys = true }
                        onResult(json.decodeFromString(it))
                    }
                } else {
                    Log.e("DataSource", "Failed to fetch JSON: ${response.message}")
                    onResult(null)
                }
            } catch (e: Exception) {
                Log.e("DataSource", "Exception: $e")
                onResult(null)
            }
        }
    }
}