package com.zlingsmart.base.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.zlingsmart.base.R
import com.zlingsmart.base.util.getVmClazz
import com.zlingsmart.base.util.inflateBindingWithGeneric
import com.zlingsmart.base.viewmodel.BaseViewModel

/**
 * 描述:使用DataBinding和ViewModel的dialog，适用于处理有复杂业务逻辑的dialog
 * 重写dialogSize传入宽高
 */
abstract class BaseVmDbDialog<VM:BaseViewModel,DB:ViewDataBinding>: DialogFragment() {

    lateinit var mDatabind: DB
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE,0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(com.google.android.material.R.color.mtrl_btn_transparent_bg_color)
        dialog?.window?.decorView?.setPadding(0,0,0,0)
        mDatabind = inflateBindingWithGeneric(layoutInflater,container,false)
        mViewModel =  ViewModelProvider(this)[getVmClazz(this)]
        initView()
        registerDefUIChange()
        return mDatabind.root
    }

    /**
     * 注册 UI 事件
     */
    private fun registerDefUIChange() {
        mViewModel.loadingChange.showToast.observeInFragment(this, Observer {
            showToast(it)
        })
        mViewModel.loadingChange.showLoading.observeInFragment(this, Observer {
            showLoading(it)
        })
    }

    open fun showToast(message: String){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
    }

    open fun showLoading(isShow:Boolean){}

    override fun onStart() {
        super.onStart()
        val dialog = dialog ?: return
        val window = dialog.window ?: return
        val attributes = window.attributes
        val size = dialogSize()
        attributes.height = size.second
        attributes.width = size.first
        window.attributes = attributes
    }

    abstract fun initView()

    /**
     * @return 宽高
     */
    abstract fun dialogSize():Pair<Int,Int>

    fun show(manager: FragmentManager){
        show(manager,this.toString())
    }

    protected inline fun binding(block: DB.() -> Unit): DB {
        return mDatabind.apply(block)
    }
}