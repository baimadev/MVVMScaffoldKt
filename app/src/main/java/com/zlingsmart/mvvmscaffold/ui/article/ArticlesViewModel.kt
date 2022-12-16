package com.zlingsmart.mvvmscaffold.ui.article

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.zlingsmart.base.viewmodel.BaseViewModel
import com.zlingsmart.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : BaseViewModel() {

    val articleInfoData = homeRepository.getArticles(
        onStart = { loadingChange.showLoading.postValue(true) },
        onComplete = { loadingChange.showLoading.postValue(false) },
        onError = { loadingChange.showToast.postValue("请求出错") }
    ).asLiveData()

    fun saveArticleData() {
        Timber.d("保存")
        if (articleInfoData.value == null) {
            loadingChange.showToast.postValue("没有数据")
            return
        }

        viewModelScope.launch {
            homeRepository.saveArticleData(
                articleInfoData.value!!,
                onStart = { loadingChange.showLoading.postValue(true) },
                onComplete = { loadingChange.showLoading.postValue(false) },
                onError = { loadingChange.showToast.postValue("请求出错") }
            ).collect {
                loadingChange.showToast.postValue("保存成功")
            }
        }
    }
}