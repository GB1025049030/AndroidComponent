package com.wangzhen.baselib.http.remote

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.wangzhen.baselib.http.ApiResponse
import com.wangzhen.baselib.http.Resource

/**
 * Description: 请求数据的父类
 * RequestParamsType: 请求的参数
 * ResultType：返回的数据类型
 *
 * @author wangzhen
 * @version 1.0
 */
abstract class NetworkBoundResource<RequestParamsType, ResultType> {
    private val result = MediatorLiveData<Resource<ResultType>>()

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

    protected fun getValue(): Resource<ResultType>? {
        return result.value
    }

    /**
     * 先从本地拉取然后再从远端拉取
     */
    protected fun fetchFromLocalAndRemote(params: RequestParamsType?) {
        val localResponse = loadFromLocal(params)
        if (localResponse != null) {
            result.addSource(localResponse) {
                result.removeSource(localResponse)
                if (it != null) {
                    setValue(Resource.localSuccess(it))
                }
                fetchFromRemote(params)
            }
        }
    }

    /**
     * 从远端拉取数据
     */
    protected fun fetchFromRemote(params: RequestParamsType?) {
        val apiResponse = createCall(params)
        result.addSource(apiResponse) {
            result.removeSource(apiResponse)
            when (it) {
                is ApiResponse.ApiSuccessResponse -> {
                    saveResultToLocal(it.body)
                    result.value = Resource.remoteSuccess(it.body)
                }

                is ApiResponse.ApiEmptyResponse -> {
                    // todo
                }

                is ApiResponse.ApiErrorResponse -> {
                    onFetchFailed()
                    result.value = Resource.error(null, it.errorMessage)
                }
            }
        }
    }

    /**
     * 持久化 remote 返回的数据
     */
    protected abstract fun saveResultToLocal(data: ResultType)

    /**
     * 加载持久化数据
     */
    protected abstract fun loadFromLocal(params: RequestParamsType?): LiveData<ResultType>?

    /**
     * 创建一个网络请求
     */
    protected abstract fun createCall(params: RequestParamsType?): LiveData<ApiResponse<ResultType>>

    /**
     * 请求数据
     */
    abstract fun startLoad(params: RequestParamsType?, data: ResultType?)
}
