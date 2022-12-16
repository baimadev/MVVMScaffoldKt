package com.zlingsmart.mvvmscaffold

import com.zlingsmart.base.BaseApp
import com.zlingsmart.mvvmscaffold.event.AppViewModel
import dagger.hilt.android.HiltAndroidApp
import com.zlingsmart.mvvmscaffold.event.EventViewModel
import timber.log.Timber

//Application全局的ViewModel，里面存放了一些账户信息，基本配置信息等
val appViewModel: AppViewModel by lazy { App.appViewModelInstance }

//Application全局的ViewModel，用于发送全局通知操作
val eventViewModel: EventViewModel by lazy { App.eventViewModelInstance }

@HiltAndroidApp
class App: BaseApp() {

    companion object{
        lateinit var instance: App
        lateinit var eventViewModelInstance: EventViewModel
        lateinit var appViewModelInstance: AppViewModel
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        eventViewModelInstance = getAppViewModelProvider()[EventViewModel::class.java]
        appViewModelInstance = getAppViewModelProvider()[AppViewModel::class.java]

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("TimberInitializer is initialized.")
        }
    }
}