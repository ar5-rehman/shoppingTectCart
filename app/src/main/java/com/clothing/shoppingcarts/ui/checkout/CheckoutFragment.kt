package com.clothing.shoppingcarts.ui.checkout

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.RelativeLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ar7lab.cherieapp.base.extension.observe
import com.clothing.shoppingcarts.R
import com.clothing.shoppingcarts.base.delegate.viewBinding
import com.clothing.shoppingcarts.base.extension.setOnDebouncedClickListener
import com.clothing.shoppingcarts.base.fragment.BaseFragment
import com.clothing.shoppingcarts.base.navigation.NavManager
import com.clothing.shoppingcarts.cartItems
import com.clothing.shoppingcarts.databinding.FragmentCheckoutBinding
import com.clothing.shoppingcarts.databinding.ThanksDialogBinding
import com.clothing.shoppingcarts.di.SharePreferencesManager
import com.clothing.shoppingcarts.utils.CATEGORY_ID
import com.clothing.shoppingcarts.utils.showCustomProgressBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CheckoutFragment : BaseFragment(R.layout.fragment_checkout) {

    private val binding: FragmentCheckoutBinding by viewBinding()

    @Inject
    lateinit var sharePreferencesManager: SharePreferencesManager

    @Inject
    lateinit var navManager: NavManager

    private val viewModel: CheckoutViewModel by viewModels()

    private var dialog: Dialog? = null
    private var categoryID: Int = -1

    // Observe ViewModel's state to take action on UI
    private val stateObserver = Observer<CheckoutViewModel.ViewState> {

        if(it.isError){
            dialog!!.dismiss()
            showError(it.message)
        }

        /*if(it.isLoading){
            dialog!!.show()
        }*/

        if(it.status){

            dialog!!.dismiss()

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.stateLiveData, stateObserver)

        dialog = showCustomProgressBar(requireContext(), layoutInflater)

        arguments?.let {
            categoryID = it.getInt(CATEGORY_ID)
        }

        createEpoxy()

        binding.ivBack.setOnDebouncedClickListener {
            findNavController().navigateUp()
        }

        binding.buyBtn.setOnDebouncedClickListener {
            thanksForOrderDialog()
        }
    }

    private fun createEpoxy(){
        binding.rvItems.withModels {
            if (view == null)
                return@withModels

            (0..4).forEachIndexed { index, i ->
                cartItems {
                    id("cart_items")
                }
            }
        }
    }

    private fun thanksForOrderDialog() {
        val bind: ThanksDialogBinding =
            ThanksDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(bind.root)

        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.window?.setGravity(Gravity.FILL_HORIZONTAL)
        alertDialog.show()

        bind.tvEnter.setOnClickListener {
            alertDialog.dismiss()
        }

        bind.ivClose.setOnClickListener {
            alertDialog.dismiss()
        }
    }

}