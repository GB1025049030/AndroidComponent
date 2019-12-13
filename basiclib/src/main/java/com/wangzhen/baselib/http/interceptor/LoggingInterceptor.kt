package com.wangzhen.baselib.http.interceptor

import android.util.Log
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset
import kotlin.text.Charsets.UTF_8

/**
 * Description: 打印网络请求和响应，更详细的参考[https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/src/main/java/okhttp3/logging/HttpLoggingInterceptor.kt]
 *
 * @author wangzhen
 * @version 1.0
 */
class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestLogBuilder: StringBuilder = StringBuilder()
        val responseLogBuilder: StringBuilder = StringBuilder()
        val request = chain.request()
        requestLogBuilder.append("\n>>>>>>>>>>>>>>>>>>>>>>Request>>>>>>>>>>>>>>>>>>>>>>>>>>\n")
        requestLogBuilder.append("[${request.method}]url:${request.url}\n")
        appendHeaders(request.headers,requestLogBuilder)
        val requestBody = request.body
        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            val contentType = requestBody.contentType()
            val charset: Charset = contentType?.charset(UTF_8) ?: UTF_8
            requestLogBuilder.append("Content-Type: $contentType")
            requestLogBuilder.append("Content-Length: ${requestBody.contentLength()}")
            if (buffer.size < 200) {
                requestLogBuilder.append("Request-Body:${buffer.readString(charset)}")
            } else {
                requestLogBuilder.append("Request-Body: the body is toot large !")
            }
            buffer.close()
        }

        requestLogBuilder.append("}\n")
        requestLogBuilder.append(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n")
        Log.i("LoggingInterceptor", requestLogBuilder.toString())

        val response = chain.proceed(request)
        responseLogBuilder.append("\n<<<<<<<<<<<<<<<<<<<<<Response<<<<<<<<<<<<<<<<<<<<<<<<<<\n")
        responseLogBuilder.append("[${request.method}]url:${request.url}\n")
        appendHeaders(response.headers,responseLogBuilder)
        responseLogBuilder.append("Code:${response.code}\n")
        // 注意此处不要使用 response.body?.string(），response.body may be consumed only once
        val responseBody = response.body
        val responseBodySource = responseBody?.source()
        if (responseBodySource != null) {
            val buffer = responseBodySource.buffer
            responseLogBuilder.append(
                "Response-Body:{\n ${buffer.readString(
                    responseBody.contentType()?.charset(
                        UTF_8
                    ) ?: UTF_8
                )}\n}\n"
            )
        }

        responseLogBuilder.append("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n")
        Log.i("LoggingInterceptor", responseLogBuilder.toString())

        return response
    }

    private fun appendHeaders(headers:Headers,stringBuilder: StringBuilder){
        stringBuilder.append("headers:{\n")
        headers.forEach {
            stringBuilder.append(" ${it.first} : ${it.second},\n")
        }
    }
}
