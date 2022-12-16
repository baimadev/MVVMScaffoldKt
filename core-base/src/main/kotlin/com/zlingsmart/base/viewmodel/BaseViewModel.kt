package com.zlingsmart.base.viewmodel

import androidx.lifecycle.ViewModel
import com.kunminx.architecture.ui.callback.UnPeekLiveData

open class BaseViewModel: ViewModel() {

    val loadingChange:UiLoadingChange by lazy { UiLoadingChange() }

    inner class UiLoadingChange{
        val showToast by lazy { UnPeekLiveData<String>() }
        val showLoading by lazy { UnPeekLiveData<Boolean>() }
    }

}