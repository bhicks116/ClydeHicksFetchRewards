package com.darkstarstudios.fetchrewardsexercise.datasource

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody

import org.junit.Test
import org.junit.Assert.*

class DataSourceTest {

    @Test
    fun dataSource_fetchRewardsNotNull() {
        var clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor( MockInterceptor() )
        val client = clientBuilder.build()

        val dataSource = DataSource()

        dataSource.fetchRewards("https://example.com/gooddata", onResult = { rewards ->
            assertNotNull(rewards)
            assertTrue(rewards!!.isNotEmpty())
        }, client)
    }

    @Test
    fun dataSource_fetchRewardsNull() {
        var clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor( MockInterceptor() )
        val client = clientBuilder.build()

        val dataSource = DataSource()

        dataSource.fetchRewards("https://example.com/nulldata", onResult = { rewards ->
            assertNull(rewards)
        }, client)
    }

    @Test
    fun dataSource_fetchRewardsBadData() {
        var clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor( MockInterceptor() )
        val client = clientBuilder.build()

        val dataSource = DataSource()

        dataSource.fetchRewards("https://example.com/baddata", onResult = { rewards ->
            assertNull(rewards)
        }, client)
    }

    @Test
    fun dataSource_fetchRewardsNetworkError() {
        var clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor( MockInterceptor() )
        val client = clientBuilder.build()

        val dataSource = DataSource()

        dataSource.fetchRewards("https://example.com/badnetwork", onResult = { rewards ->
            assertNull(rewards)
        }, client)
    }

    class MockInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            // Get the request URL to determine if we should mock this request
            val request = chain.request()
            val url = request.url.toString()

            // Check if the URL matches the one you want to mock
            if (url.contains("badnetwork")) {
                return chain.proceed(request).newBuilder()
                    .code(500)
                    .message("ERROR")
                    .build()
            }

            var dummyJson = ""

            if (url.contains("gooddata")) {
                dummyJson = """
                {
                    "id": 1,
                    "listId": 123,
                    "name": "Mocked Reward"                   
                }
                """.trimIndent()
            } else if (url.contains("nulldata")) {
                dummyJson = ""
            } else if (url.contains("baddata")) {
                dummyJson = """
                {
                    "id": 1,
                    "listId": "not a number",
                    "name": 12345                   
                }
            """.trimIndent()
            }

            return chain.proceed(request).newBuilder()
                .code(200)
                .message("OK")
                .body(ResponseBody.create("application/json".toMediaType(), dummyJson))
                .build()
        }
    }
}