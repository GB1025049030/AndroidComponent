package com.wangzhen.commonui.framework.recyclerview

/**
 * Description: RecyclerView Item 局部刷新
 *
 * @author wangzhen
 * @version 1.0
 */
interface OnPayloadCallback {
    fun onPayload(model: Any?, payloads: List<Any>)
}
