package com.wangzhen.baselib.http.calladapter

import androidx.lifecycle.LiveData
import com.wangzhen.baselib.http.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Description:
 * retrofit 的 adapter
 * 将 Call 转换为 LiveData<ApiResponse>
 *
 * @author wangzhen
 * @version 1.0
 */
class LiveDataCallAdapter<R>(private val responseType: Type) : CallAdapter<R, LiveData<ApiResponse<R>>> {
    override fun responseType() = responseType

    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        return object : LiveData<ApiResponse<R>>(){
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if(started.compareAndSet(false,true)){
                    call.enqueue(object :Callback<R>{
                        override fun onFailure(call: Call<R>, t: Throwable) {
                            postValue(ApiResponse.create(t.message?:"unknown error"))
                        }

                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(ApiResponse.create(response))
                        }
                    })
                }
            }
        }
    }
}
