package com.wangzhen.baselib.http.remote

import androidx.lifecycle.LiveData
import com.wangzhen.baselib.http.Resource

/**
 * Description:只请求网络
 *
 * @author wangzhen
 * @version 1.0
 */
abstract class RemoteOnlyResource<ResultType, ResponseType> :
    NetworkBoundResource<ResultType, ResponseType>() {
    override fun saveResultToLocal(data: ResponseType) {
        // do nothing
    }

    override fun loadFromLocal(params: ResultType?): LiveData<ResponseType>? {
        return null
    }

    override fun startLoad(params: ResultType?, data: ResponseType?) {
        if (shouldFetch(data)) {
            setValue(Resource.loading(null))
            fetchFromRemote(params)
        }
    }
}
