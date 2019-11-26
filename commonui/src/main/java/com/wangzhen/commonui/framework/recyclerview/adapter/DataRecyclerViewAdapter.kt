package com.wangzhen.commonui.framework.recyclerview.adapter

import androidx.recyclerview.widget.RecyclerView


/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
abstract class DataRecyclerViewAdapter<VH : RecyclerView.ViewHolder, T> :
    RecyclerView.Adapter<VH>() {

    var dataList = mutableListOf<T>()

    fun <R : T> add(model: R, position: Int) {
        val realPosition = if (position == LAST_POSITION) itemCount else position
        dataList.add(realPosition, model)
        notifyItemInserted(realPosition)
        notifyItemPositionChange(realPosition)
    }

    fun remove(position: Int) {
        var realPosition = position
        if (position == LAST_POSITION && itemCount > 0) {
            realPosition = itemCount - 1
        }

        if (realPosition in (LAST_POSITION + 1) until itemCount) {
            dataList.removeAt(realPosition)
            notifyItemRemoved(realPosition)
            notifyItemPositionChange(realPosition)
        }
    }

    fun <R : T> remove(model: R) {
        dataList.forEachIndexed { index, t ->
            if (t == model) {
                remove(index)
            }
        }
    }

    fun <R : T> setData(dataList: List<R>) {
        this.dataList = dataList.toMutableList()
        notifyDataSetChanged()
    }

    fun <R : T> setDataWithoutNotify(dataList: List<R>) {
        this.dataList = dataList.toMutableList()
    }

    fun <R : T> updateData(dataList: List<R>) {
        if (this.dataList == null) {
            this.dataList = mutableListOf()
        }
        val count = this.dataList.size
        this.dataList.clear()
        notifyItemRangeRemoved(0, count)
        this.dataList.addAll(dataList)
        notifyItemRangeInserted(0, this.dataList.size)
    }

    fun getData(): List<T> {
        return this.dataList
    }

    fun getItem(position: Int): T? {
        if (position >= this.dataList.size) {
            return null
        }
        return this.dataList[position]
    }

    fun clear() {
        if (this.dataList != null) {
            this.dataList.clear()
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (this.dataList.isNullOrEmpty()) 0 else this.dataList.size
    }

    private fun notifyItemPositionChange(position: Int) {
        for (i in position until itemCount) {
            notifyItemChanged(i)
        }
    }

    companion object {
        private const val LAST_POSITION = -1
    }
}
