package com.zlingsmart.base.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.zlingsmart.base.util.inflateBindingWithGeneric

/**
 * 描述:使用ViewBinding的简单dialog，适用于不需要viewmodel，只显示内容和处理简单点击事件的dialog
 * 重新dialogSize传入宽高
 */
abstract class BaseSimpleDialog<T:ViewBinding>: DialogFragment() {
    lateinit var mViewBind: T

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        mViewBind = inflateBindingWithGeneric(inflater,null,false,0)
        val dialog = Dialog(requireContext())
        dialog.setContentView(mViewBind.root)
        initView()
        return dialog
    }

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
     * 宽高
     */
    abstract fun dialogSize():Pair<Int,Int>

    fun show(manager: FragmentManager){
        show(manager,this.toString())
    }

}