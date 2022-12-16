package com.zlingsmart.mvvmscaffold.event

import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.zlingsmart.base.viewmodel.BaseViewModel

/**
 * 描述　:APP全局的ViewModel，可以在这里发送全局通知替代EventBus，LiveDataBus等
 */
class EventViewModel : BaseViewModel() {

    val todoEvent = UnPeekLiveData<Boolean>()

}