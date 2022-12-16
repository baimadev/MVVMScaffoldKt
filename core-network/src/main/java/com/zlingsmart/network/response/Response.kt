package com.zlingsmart.network.response

import com.zlingsmart.network.Settings
import timber.log.Timber


interface Response<T> {

    fun isSuccessful(): Boolean
    fun getMessage(): String?
    fun getResponseData(): T?

    fun executeResponse(successResponse: ((Response<T>) -> Unit)?, error: ((String) -> Unit)? = null) {
        if (this.isSuccessful()) {
            successResponse?.invoke(this)
            return
        }

        (this.getMessage() ?: Settings.MESSAGE_EMPTY).let {
            error?.invoke(it) ?: Timber.e("responseError",it)
        }
    }

    fun execute(successResponse: ((T?) -> Unit)?, error: ((String) -> Unit)? = null) {
        if (this.isSuccessful()) {
            successResponse?.invoke(this.getResponseData())
            return
        }

        (this.getMessage() ?: Settings.MESSAGE_EMPTY).let {
            error?.invoke(it) ?: Timber.e("responseError",it)
        }
    }

}