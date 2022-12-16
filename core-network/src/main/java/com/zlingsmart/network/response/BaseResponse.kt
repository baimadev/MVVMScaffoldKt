package com.zlingsmart.network.response

import com.squareup.moshi.Json
import timber.log.Timber

data class BaseResponse<T>(
    @Json(name = "data")
    private var data: T?,

    @Json(name = "errorCode")
    var code: Int = -1,

    @Json(name = "errorMsg")
    var msg: String = ""
) : Response<T> {
    override fun isSuccessful(): Boolean = code == ResponseCode.SUCCESS

    override fun getMessage(): String = msg

    override fun getResponseData(): T? = data
}

const val TAG = "NetworkResponse"

fun <T> BaseResponse<T>.getResult(): T {
    if (!isSuccessful() || getResponseData() == null) {
        when(code){
//            ResponseCode.UNAUTHORIZED->{
//                Log.e(TAG,"认证失败：$code ${getMessage()} ")
//                throw NetworkException(code,getMessage())
//            }
//            ResponseCode.LOGIN_EXPIRED ->{
//                Log.e(TAG,"登录已过期：$code ${getMessage()} ")
//                throw NetworkException(code,getMessage())
//            }
//            ResponseCode.SERVER_UNUSUAL->{
//                Log.e(TAG,"请求错误：$code ${getMessage()} ")
//                throw NetworkException(code,getMessage())
//            }
            else ->{
                Timber.e(TAG,"网络请求失败：$code ${getMessage()} ")
                throw NetworkException(code,getMessage())
            }
        }
    }
    return getResponseData()!!
}
