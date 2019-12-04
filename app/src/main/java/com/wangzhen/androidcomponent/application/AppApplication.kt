package com.wangzhen.androidcomponent.application

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}
