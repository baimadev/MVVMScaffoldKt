package com.zlingsmart.mvvmscaffold.ui.home

import android.os.Bundle
import com.zlingsmart.base.activity.BaseVmDbActivity
import com.zlingsmart.mvvmscaffold.appViewModel
import com.zlingsmart.mvvmscaffold.databinding.ActivityHomeBinding
import com.zlingsmart.mvvmscaffold.ui.dialog.VmVbDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseVmDbActivity<MainViewModel,ActivityHomeBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {
//        val dialog = NoticeDialog()
//        dialog.show(supportFragmentManager)
        val dialog = VmVbDialog{
            appViewModel.userInfo.postValue("click confirm")
        }
        dialog.show(supportFragmentManager)
    }

    override fun showLoading(isShow: Boolean) {
    }

    override fun createObserver() {

    }

}