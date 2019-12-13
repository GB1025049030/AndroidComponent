package com.wangzhen.baselib.http

import retrofit2.Response

/**
 * Description:主要用来标记远端返回的 response 的状态：成功，失败，空
 *
 * @author wangzhen
 * @version 1.0
 */
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(msg: String = "unkown error"): ApiErrorResponse<T> {
            return ApiErrorResponse(msg)
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body)
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(errorMsg ?: "unkown error")
            }
        }
    }

    class ApiEmptyResponse<T> : ApiResponse<T>()

    data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

    data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()
}
