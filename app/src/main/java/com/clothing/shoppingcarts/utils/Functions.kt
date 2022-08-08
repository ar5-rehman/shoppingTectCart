package com.clothing.shoppingcarts.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.clothing.shoppingcarts.databinding.ProgressDialogBinding

fun showCustomProgressBar(context: Context, layoutInflater: LayoutInflater): Dialog {
    val bind: ProgressDialogBinding = ProgressDialogBinding.inflate(layoutInflater)
    val dialog = Dialog(context)
    dialog.setContentView(bind.root)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.window?.setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
    dialog.setCancelable(false)
    return dialog

}