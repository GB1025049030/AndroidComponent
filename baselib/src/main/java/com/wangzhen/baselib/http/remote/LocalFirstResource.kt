package com.wangzhen.baselib.http.remote

/**
 * Description:只第一次请求的时候请求一次本地数据
 *
 * @author wangzhen
 * @version 1.0
 */
abstract class LocalFirstResource<ResultType, ResponseType> :
    NetworkBoundResource<ResultType, ResponseType>() {
    override fun startLoad(params: ResultType?, data: ResponseType?) {
        if (getValue() != null) {
            fetchFromLocalAndRemote(params)
        } else {
            fetchFromRemote(params)
        }
    }
}
