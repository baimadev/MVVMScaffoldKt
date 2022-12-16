package com.zlingsmart.base.activity

import android.view.View
import androidx.databinding.ViewDataBinding
import com.zlingsmart.base.util.inflateBindingWithGeneric
import com.zlingsmart.base.viewmodel.BaseViewModel

/**
 * 描述　: 包含ViewModel 和Databind ViewModelActivity基类，把ViewModel 和Databind注入进来了
 * 需要使用Databind的清继承它
 */
abstract class BaseVmDbActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmActivity<VM>() {

    override fun layoutId() = 0

    lateinit var mDatabind: DB

    /**
     * 创建DataBinding
     */
    override fun initDataBind(): View? {
        mDatabind = inflateBindingWithGeneric(layoutInflater)
        return mDatabind.root
    }

    protected inline fun binding(block: DB.() -> Unit): DB {
        return mDatabind.apply(block)
    }
}