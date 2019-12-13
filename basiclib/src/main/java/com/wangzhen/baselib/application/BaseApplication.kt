package com.wangzhen.baselib.application

import android.app.Application

/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
open class BaseApplication : Application() {
    private lateinit var appContext: BaseApplication
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    fun getContext(): Application {
        return appContext
    }
}
