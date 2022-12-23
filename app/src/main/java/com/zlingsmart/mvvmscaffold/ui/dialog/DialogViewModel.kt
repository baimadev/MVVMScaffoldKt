package com.zlingsmart.mvvmscaffold.ui.dialog

import androidx.lifecycle.asLiveData
import com.zlingsmart.base.viewmodel.BaseViewModel
import com.zlingsmart.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DialogViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : BaseViewModel() {
    fun test() = homeRepository.getArticles(
        onStart = {},
        onComplete = { loadingChange.showToast.postValue("加载成功") },
        onError = {}).asLiveData()
}