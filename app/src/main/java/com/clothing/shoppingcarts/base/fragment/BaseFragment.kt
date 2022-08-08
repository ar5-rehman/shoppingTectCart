package com.clothing.shoppingcarts.base.fragment

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.clothing.shoppingcarts.R
import com.clothing.shoppingcarts.base.extension.setOnDebouncedClickListener
import com.clothing.shoppingcarts.databinding.DialogCommonBinding
import com.clothing.shoppingcarts.di.SharePreferencesManager
import com.google.android.material.snackbar.Snackbar

/**
 * This open class will have common function for Fragment
 * @param contentLayoutId layout of any fragment
 */
open class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    /**
     * This open method will detect if an fragment need to set light status bar or not
     * In any fragment that extends BaseFragment, may override this method
     * @return true if an fragment need to set light status bar
     * (because that fragment have background white),
     * false if an fragment does not need to set light status bar
     */
    open fun isNeedWindowLightStatusBar(): Boolean = true

    private lateinit var sharePreferencesManager: SharePreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isNeedWindowLightStatusBar()) {
            setWindowLightStatus()
        } else {
            clearWindowLightStatus()
        }

        sharePreferencesManager = SharePreferencesManager(requireContext())
    }

    //hide keyboard from any fragment
    fun hideKeyboard() {
        requireActivity().currentFocus?.let { view ->
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * Clear window light status bar to make the status bar icon appear on black background
     */
    private fun clearWindowLightStatus() {
        WindowInsetsControllerCompat(
            requireActivity().window,
            requireActivity().window.decorView
        ).isAppearanceLightStatusBars = false
    }

    /**
     * Clear window light status bar to make the status bar icon appear on white background
     */
    private fun setWindowLightStatus() {
        WindowInsetsControllerCompat(
            requireActivity().window,
            requireActivity().window.decorView
        ).isAppearanceLightStatusBars = true
    }

    /**
     * Show a snack bar to display error message
     */
    fun showError(message: String?) {
        if (message == null) return

        val bind: DialogCommonBinding = DialogCommonBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setView(bind.root)

        bind.tvTitle.text = getString(R.string.alert)
        bind.tvDescription.text = message

        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.window?.setGravity(Gravity.CENTER)
        alertDialog.show()

        bind.ivClose.setOnDebouncedClickListener {
            alertDialog.dismiss()
        }
    }

    /**
     * Show a snack bar to display error message
     */
    fun showMessage(message: String?) {
        if (message == null) return

        val bind: DialogCommonBinding = DialogCommonBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setView(bind.root)

        bind.tvTitle.text = getString(R.string.alert)
        bind.tvDescription.text = message

        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.window?.setGravity(Gravity.CENTER)
        alertDialog.show()

        bind.ivClose.setOnDebouncedClickListener {
            alertDialog.dismiss()
        }
    }
}