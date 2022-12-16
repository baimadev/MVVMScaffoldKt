package com.zlingsmart.network.response

class NetworkException(val code:Int, msg:String) : Exception(msg) {


    companion object{
        /**
         * 网路错误
         * */
        const val NETWORK_FAILURE = 0

        /**
         * token 失效
         * */
        const val TOKEN_FAILURE = 1

        /**
         * 服务端报错
         * */
        const val SERVER_FAILURE = 2

        /**
         * 请求错误
         * */
        const val REQUEST_FAILURE = 3

        /**
         * 未知异常
         * */
        const val UNKNOWN_FAILURE = 4
    }
}