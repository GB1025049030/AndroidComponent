package com.wangzhen.baselib.http.remote

import androidx.lifecycle.LiveData

/**
 * Description:只请求网络
 *
 * @author wangzhen
 * @version 1.0
 */
abstract class RemoteOnlyResource<ResultType, ResponseType> : NetworkBoundResource<ResultType, ResponseType>(){
    override fun saveResultToLocal(data: ResponseType) {
        // do nothing
    }

    override fun loadFromLocal(): LiveData<ResultType>? {
        return null
    }
}
