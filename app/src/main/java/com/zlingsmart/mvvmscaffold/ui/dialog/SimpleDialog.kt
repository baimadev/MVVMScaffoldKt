package com.zlingsmart.mvvmscaffold.ui.dialog

import com.zlingsmart.base.dialog.BaseSimpleDialog
import com.zlingsmart.mvvmscaffold.databinding.DialogLogoutBinding

class SimpleDialog: BaseSimpleDialog<DialogLogoutBinding>() {

    override fun initView() {
        isCancelable = false
        mViewBind.btCancel.setOnClickListener {
            dismiss()
        }
        mViewBind.btConfirm.setOnClickListener {
            dismiss()
        }
        mViewBind.tvTitle.text = "SimpleDialog"
    }

    override fun dialogSize() = 800 to 500


}