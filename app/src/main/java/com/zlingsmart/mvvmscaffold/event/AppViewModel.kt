package com.zlingsmart.mvvmscaffold.event

import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.zlingsmart.base.viewmodel.BaseViewModel

/**
 * 描述　:APP全局的ViewModel，可以存放公共数据，当他数据改变时，所有监听他的地方都会收到回调,也可以做发送消息
 */
class AppViewModel : BaseViewModel() {

    //var userInfo = UnPeekLiveData.Builder<UserInfo>().setAllowNullValue(true).create()

}