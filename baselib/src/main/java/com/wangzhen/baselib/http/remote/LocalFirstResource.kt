package com.wangzhen.baselib.http.remote

/**
 * Description:只在创建的时候请求一次本地数据
 *
 * @author wangzhen
 * @version 1.0
 */
abstract class LocalFirstResource <ResultType, ResponseType>: NetworkBoundResource<ResultType, ResponseType>(){

}
