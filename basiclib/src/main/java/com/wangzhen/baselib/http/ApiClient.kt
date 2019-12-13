package com.wangzhen.baselib.http

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.wangzhen.baselib.BuildConfig
import com.wangzhen.baselib.http.calladapter.LiveDataCallAdapterFactory
import com.wangzhen.baselib.http.interceptor.HeaderInterceptor
import com.wangzhen.baselib.http.interceptor.LoggingInterceptor
import com.wangzhen.baselib.http.interceptor.MockInterceptor
import com.wangzhen.baselib.http.service.GithubService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 类似于服务注册机，包含多个 retrofit 实例
 */
class ApiClient private constructor() {
    private val okHttpClient = createOkHttpClient()
    val githubService: GithubService

    init {
        githubService =
            createService(okHttpClient, ApiHost.getGithubHost(), GithubService::class.java)
    }

    private fun <T> createService(
        okHttpClient: OkHttpClient,
        baseUrl: String,
        service: Class<T>
    ): T {
        return Retrofit.Builder().client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(service)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(HeaderInterceptor(mutableMapOf(Pair("Token", "testToken"))))

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(LoggingInterceptor())
            builder.addInterceptor(MockInterceptor())
        }

        return builder.build()
    }

    companion object {
        private var instance: ApiClient? = null

        fun getInstance(): ApiClient {
            if (instance == null) {
                synchronized(ApiClient::class.java) {
                    if (instance == null) {
                        instance = ApiClient()
                    }
                }
            }
            return instance!!
        }
    }
}
