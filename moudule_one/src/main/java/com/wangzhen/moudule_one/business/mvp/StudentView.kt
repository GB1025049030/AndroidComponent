package com.wangzhen.moudule_one.business.mvp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.wangzhen.commonui.framework.mvp.BaseView
import com.wangzhen.moudule_one.R

/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
class StudentView(context: Context?, attrs: AttributeSet?) : BaseView,
    LinearLayout(context, attrs) {
    override fun getView(): View {
        return this
    }
    companion object{
        fun newInstance(parent:ViewGroup):StudentView{
            return LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item, parent, false) as StudentView
        }
    }
}
