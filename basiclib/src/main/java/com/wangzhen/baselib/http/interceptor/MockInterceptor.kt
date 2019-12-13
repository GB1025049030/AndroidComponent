package com.wangzhen.baselib.http.interceptor

import com.wangzhen.baselib.http.mock.getMock
import com.wangzhen.baselib.http.mock.getMockData
import com.wangzhen.baselib.http.mock.getRetrofitMethod
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * Description: mock 拦截器
 *
 * @author wangzhen
 * @version 1.0
 */
class MockInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // 获取 retrofit 中定义 method
        val retrofitMethod = getRetrofitMethod(request) ?: return chain.proceed(request)

        // 根据 method 获取它的 mock 注解
        val mock = getMock(retrofitMethod) ?: return chain.proceed(request)

        // 获取 mockUrl 进行重定向
        if (mock.url.isNotEmpty()) {
            return chain.proceed(mockRequest(request, mock.url))
        }

        // 根据 mock 注解获取 mockData
        val mockData = getMockData(mock)

        // 如果 mockData 不为空就短路拦截器
        if (!mockData.isNullOrEmpty()) {
            return Response.Builder()
                .protocol(Protocol.HTTP_1_0)
                .code(200)
                .request(request)
                .message("ok")
                .body(mockData.toResponseBody(null))
                .build()
        }
        return chain.proceed(request)
    }

    private fun mockRequest(request: Request, mockUrl: String): Request {
        return request.newBuilder().url(mockUrl).build()
    }
}
