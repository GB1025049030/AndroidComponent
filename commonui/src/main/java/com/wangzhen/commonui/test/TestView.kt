package com.wangzhen.commonui.test

import android.content.Context
import android.view.View
import com.wangzhen.commonui.framework.mvp.BaseView

/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
class TestView(context: Context?) :BaseView, View(context) {
    override fun getView(): View {
        return this
    }
}
