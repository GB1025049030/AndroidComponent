package com.wangzhen.baselib.application

import android.content.Context

/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
object GlobalApplication {
    private lateinit var application: BaseApplication

    fun initApplication(baseApplication: BaseApplication){
        application = baseApplication
    }

    fun getAppContext():Context{
        return application.getContext()
    }
}
