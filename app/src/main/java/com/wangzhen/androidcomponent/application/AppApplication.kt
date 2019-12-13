package com.wangzhen.androidcomponent.application

import com.facebook.stetho.Stetho
import com.wangzhen.baselib.application.BaseApplication
import com.wangzhen.baselib.application.GlobalApplication

/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
class AppApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        GlobalApplication.initApplication(this)
        Stetho.initializeWithDefaults(this)
    }
}
