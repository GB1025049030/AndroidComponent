package com.wangzhen.commonui.framework.recyclerview.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wangzhen.commonui.framework.mvp.BaseModel
import com.wangzhen.commonui.framework.mvp.BasePresenter
import com.wangzhen.commonui.framework.mvp.BaseView
import com.wangzhen.commonui.framework.recyclerview.OnPayloadCallback
import java.util.*

/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
abstract class BaseRecyclerAdapter<Model : BaseModel> :
    DataRecyclerViewAdapter<BaseRecyclerAdapter.BaseViewHolder, Model>() {

    private val emptyPresenterCreator = EmptyPresenterCreator()

    /**
     * 所有创建的 presenter
     */
    private val createdPresenters = HashSet<BasePresenter<BaseView, BaseModel>>()

    /**
     * Model -> ViewType
     */
    private val itemViewTypeMap = HashMap<Class<out BaseModel>, Int>()

    /**
     * 创建 View 的方法列表
     * Index 即 ViewType
     */
    private val viewCreatorList = ArrayList<ViewCreator<*>>()

    /**
     * 创建 Presenter 的方法列表
     * Index 即 ViewType
     */
    private val presenterCreatorList = ArrayList<PresenterCreator<BaseView, BaseModel>>()

    init {
        this.registerMVP()
    }

    /**
     * 子类需要调用 register 逐个注册用到的 model
     */
    abstract fun registerMVP()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val presenter: BasePresenter<out BaseView, out BaseModel>?
        val baseView: BaseView?

        return if (viewType >= 0) {
            baseView = viewCreatorList[viewType].newView(parent)
            presenter = presenterCreatorList[viewType].newPresenter(baseView)
            if (presenter != null) {
                createdPresenters.add(presenter)
            }
            BaseViewHolder(baseView.getView(), presenter)
        } else {
            BaseViewHolder(View(parent.context), null)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder.presenter == null) {
            return
        }

        holder.presenter.viewHolder = holder
        doBind(holder.presenter, getItem(position))

    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            if (holder.presenter is OnPayloadCallback) {

            }
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val model: Model? = getItem(position)
        return if (model != null) {
            val clazz = model::class.java as Class<out BaseModel>
            getViewTypeByModel(clazz)
        } else {
            -1
        }
    }


    private fun doBind(presenter: BasePresenter<BaseView, BaseModel>, baseModel: BaseModel?) {
        if (baseModel != null) {
            // 由于 presenter 是重用的，所以先解绑再绑定
            presenter.unbind()
            presenter.bind(baseModel)
        }
    }

    /**
     * 在 recyclerView detach 时调用。释放所有资源（调用 presenter 的 unbind）
     * 不在 onViewDetachedFromWindow 释放 （调用 unbind ）的原因是因为，如果在这里全部释放以后，可能在重用的时候不会重新绑定。
     * 数据一样的时候 recyclerView 的 onBindViewHolder 不会被调用
     * todo 这一个没理解
     */
    fun onDetachFromWindow() {
        // 重用的 presenter 已经在 doBind 的时候调用过 unbind 了
        for (presenter in createdPresenters) {
            presenter.unbind()
        }
    }

    /**
     * 按注册的顺序对 dataList 排序
     */
    fun sortByRegisterOrder() {
        Collections.sort(dataList, RegisterOrderComparator())
    }

    fun <V : BaseView, M : BaseModel> register(
        clazz: Class<out M>,
        viewCreator: ViewCreator<out V>,
        presenterCreator: PresenterCreator<V, M>
    ) {
        if (itemViewTypeMap.containsKey(clazz)) {
            val message = String.format(
                Locale.CHINA,
                "Model %s already registered in this adapter. Register each model only once. ",
                clazz.name
            )
            throw IllegalStateException(message)
        }

        itemViewTypeMap[clazz] = viewCreatorList.size
        viewCreatorList.add(viewCreator)
        presenterCreatorList.add((presenterCreator) as PresenterCreator<BaseView, BaseModel>)
    }


    private fun getViewTypeByModel(clazz: Class<out BaseModel>): Int {
        return if (itemViewTypeMap.containsKey(clazz)) {
            itemViewTypeMap[clazz]!!
        } else {
            -1
        }
    }


    interface PresenterCreator<V : BaseView, M : BaseModel> {
        fun newPresenter(view: V): BasePresenter<V, M>?
    }

    interface ViewCreator<V : BaseView> {
        fun newView(parent: ViewGroup): V
    }

    private inner class RegisterOrderComparator<M : Model> : Comparator<M> {
        override fun compare(o1: M, o2: M): Int {
            val type1 = itemViewTypeMap[o1::class.java]
            val type2 = itemViewTypeMap[o2::class.java]
            return type1!!.compareTo(type2!!)
        }
    }

    class BaseViewHolder(itemView: View, val presenter: BasePresenter<BaseView, BaseModel>?) :
        RecyclerView.ViewHolder(itemView)

    private class EmptyPresenterCreator : PresenterCreator<BaseView, BaseModel> {
        override fun newPresenter(view: BaseView): BasePresenter<BaseView, BaseModel>? {
            return null
        }
    }
}
