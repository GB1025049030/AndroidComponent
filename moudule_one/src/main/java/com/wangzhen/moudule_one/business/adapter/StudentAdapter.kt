package com.wangzhen.moudule_one.business.adapter

import android.view.ViewGroup
import com.wangzhen.commonui.framework.mvp.BaseModel
import com.wangzhen.commonui.framework.mvp.BasePresenter
import com.wangzhen.commonui.framework.recyclerview.adapter.BaseRecyclerAdapter
import com.wangzhen.moudule_one.business.mvp.StudentModel
import com.wangzhen.moudule_one.business.mvp.StudentPresenter
import com.wangzhen.moudule_one.business.mvp.StudentView

/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
class StudentAdapter() : BaseRecyclerAdapter<BaseModel>() {
    override fun registerMVP() {
        register(StudentModel::class.java,
            object : ViewCreator<StudentView> {
                override fun newView(parent: ViewGroup): StudentView {
                    return StudentView.newInstance(parent)
                }

            },
            object : PresenterCreator<StudentView, StudentModel> {
                override fun newPresenter(view: StudentView): BasePresenter<StudentView, StudentModel>? {
                    return StudentPresenter(view)
                }
            })
    }
}
