package com.wangzhen.baselib.http.mock

import android.util.Log
import com.wangzhen.baselib.application.GlobalApplication
import com.wangzhen.baselib.utils.readAssets
import okhttp3.Request
import retrofit2.Invocation
import java.lang.reflect.Method

/**
 * Description: mock 相关工具工具
 *
 * @author wangzhen
 * @version 1.0
 */

fun getRetrofitMethod(request: Request): Method? {
    return request.tag(Invocation::class.java)?.method() ?: return null
}

fun getMock(method: Method): Mock? {
    val annotations = method.annotations
    for (annotation in annotations) {
        if (annotation is Mock) {
            Log.e("1---","find mock")
            return annotation
        }
    }
    return null
}

fun getMockData(mock: Mock): String? {
    val value = mock.value
    Log.e("1---","mock.value = $value")
    if (value.isNotEmpty()) {
        return value
    }
    val assetsUrl = mock.assets
    Log.e("1---","mock.assetsUrl = $assetsUrl")
    if (assetsUrl.isNotEmpty()) {
        return readAssets(GlobalApplication.getAppContext(), assetsUrl)
    }
    return null
}

fun getMockUrl(mock:Mock):String?{
    return mock.url
}
