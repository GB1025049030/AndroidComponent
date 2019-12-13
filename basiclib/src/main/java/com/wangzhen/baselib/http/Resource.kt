package com.wangzhen.baselib.http

/**
 * Description:
 * A generic class that holds a value with its loading status.
 * @author wangzhen
 * @version 1.0
 */
data class Resource<out T>(val status: ApiStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> localSuccess(data: T?): Resource<T> {
            return Resource(ApiStatus.LOCAL_SUCCESS, data, null)
        }

        fun <T> remoteSuccess(data: T?): Resource<T> {
            return Resource(ApiStatus.REMOTE_SUCCESS, data, null)
        }

        fun <T> error(data: T?, msg: String): Resource<T> {
            return Resource(ApiStatus.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(ApiStatus.LOADING, data, null)
        }
    }
}
