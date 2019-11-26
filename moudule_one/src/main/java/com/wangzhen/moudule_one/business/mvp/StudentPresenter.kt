package com.wangzhen.moudule_one.business.mvp

import com.wangzhen.commonui.framework.mvp.BasePresenter
import kotlinx.android.synthetic.main.layout_student_item.view.*

/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
class StudentPresenter(view: StudentView) : BasePresenter<StudentView, StudentModel>(view) {
    override fun bind(model: StudentModel) {
        view.tvName.text = model.name
        view.tvAge.text = model.age.toString()
    }
}
