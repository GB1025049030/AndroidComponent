package com.wangzhen.baselib.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Description: 添加 header 的拦截器
 *
 * @author wangzhen
 * @version 1.0
 */
class HeaderInterceptor(private val headers: Map<String, String>?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!headers.isNullOrEmpty()) {
            val builder = request.newBuilder()
            headers.forEach {
                builder.header(it.key, it.value)
            }
            request = builder.build()
        }
        return chain.proceed(request)
    }
}
