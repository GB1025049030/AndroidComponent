package com.wangzhen.baselib.utils

import android.content.Context

/**
 * Description: assets 相关的工具类
 *
 * @author wangzhen
 * @version 1.0
 */
fun readAssets(context: Context, fileName: String): String? {
    return context.assets.open(fileName).bufferedReader().use {
        it.readText()
    }
}
