package com.zlingsmart.mvvmscaffold.ui.web

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.Window
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.zlingsmart.base.fragment.BaseVmDbFragment
import com.zlingsmart.model.ArticleData
import com.zlingsmart.mvvmscaffold.databinding.FragmentWebBinding
import com.zlingsmart.mvvmscaffold.util.hideSoftKeyboard
import com.zlingsmart.mvvmscaffold.util.initClose
import com.zlingsmart.mvvmscaffold.util.nav
import kotlinx.android.synthetic.main.fragment_web.*
import kotlinx.android.synthetic.main.include_toolbar.*


class WebFragment : BaseVmDbFragment<WebViewModel, FragmentWebBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.run {
            //点击文章进来的
            mViewModel.showTitle = getString("title")?:""
            mViewModel.url = getString("url")?:""
//            getParcelable<ArticleData>("ariticleData")?.let {
//                mViewModel.ariticleId = it.id
//                mViewModel.showTitle = it.title
//                mViewModel.url = it.link
//            }
        }
        toolbar.run {
            mActivity.setSupportActionBar(this)
            initClose(mViewModel.showTitle){
                hideSoftKeyboard(activity)
                nav().navigateUp()
            }
        }
    }

    override fun lazyLoadData() {
        mDatabind.webcontent.run {
            loadUrl(mViewModel.url)
        }
    }

    override fun createObserver() {

    }
}