package com.wangzhen.baselib.http.remote

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.wangzhen.baselib.http.Resource

/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
abstract class NetworkBoundResource<ResultType, ResponseType> {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        // 第一次从本地读取
    }

    fun asLiveData() = result

    protected open fun onFetchFailed() {}

    protected open fun shouldFetch(data: ResultType?): Boolean {
        return true
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetWork() {

    }

    protected abstract fun saveResultToLocal(data: ResponseType)

    protected abstract fun loadFromLocal(): LiveData<ResultType>?

    protected abstract fun createCall(): LiveData<ResponseType>
}
