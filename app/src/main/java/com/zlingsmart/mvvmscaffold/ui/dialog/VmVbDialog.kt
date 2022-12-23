package com.zlingsmart.mvvmscaffold.ui.dialog

import com.zlingsmart.base.dialog.BaseVmVbDialog
import com.zlingsmart.mvvmscaffold.R
import com.zlingsmart.mvvmscaffold.databinding.DialogLogoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_logout.*
import timber.log.Timber

@AndroidEntryPoint
class VmVbDialog(val onConfirmClick:() -> Unit) : BaseVmVbDialog<DialogViewModel, DialogLogoutBinding>() {

    override fun initView() {
        mViewBind.tvTitle.text = "BaseVmVbDialog"
        mViewModel.test().observe(this) {
            Timber.d(it.toString())
        }

        mViewBind.btConfirm.setOnClickListener {
            onConfirmClick.invoke()
            dismiss()
        }
        mViewBind.btCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun dialogSize(): Pair<Int, Int> {
        return 900 to 500
    }
}