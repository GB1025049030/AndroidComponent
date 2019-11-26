package com.wangzhen.commonui.framework.mvp

import androidx.recyclerview.widget.RecyclerView

/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
abstract class BasePresenter<V : BaseView, M : BaseModel>(val view: V) {
    var viewHolder: RecyclerView.ViewHolder? = null

    abstract fun bind(model: M)

    fun unbind() {}

    fun getAdapterPosition(): Int {
        viewHolder ?: return -1
        return viewHolder!!.adapterPosition
    }
}
