package com.zlingsmart.mvvmscaffold.ui.home

import android.os.Bundle
import com.zlingsmart.base.activity.BaseVmDbActivity
import com.zlingsmart.mvvmscaffold.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseVmDbActivity<MainViewModel,ActivityHomeBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun showLoading(isShow: Boolean) {
    }

    override fun createObserver() {

    }

}