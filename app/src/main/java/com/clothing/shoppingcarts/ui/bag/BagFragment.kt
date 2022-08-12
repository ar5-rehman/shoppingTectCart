package com.clothing.shoppingcarts.ui.bag

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.carousel
import com.ar7lab.cherieapp.base.extension.observe
import com.clothing.shoppingcarts.ItemWomenClothItemsBindingModel_
import com.clothing.shoppingcarts.R
import com.clothing.shoppingcarts.base.delegate.viewBinding
import com.clothing.shoppingcarts.base.extension.setOnDebouncedClickListener
import com.clothing.shoppingcarts.base.fragment.BaseFragment
import com.clothing.shoppingcarts.base.navigation.NavManager
import com.clothing.shoppingcarts.databinding.FragmentBagBinding
import com.clothing.shoppingcarts.di.SharePreferencesManager
import com.clothing.shoppingcarts.itemBagItems
import com.clothing.shoppingcarts.itemWomenClothItemMainImg
import com.clothing.shoppingcarts.ui.shop.item.ItemListener
import com.clothing.shoppingcarts.ui.shop.item.WomenClothItemFragmentDirections
import com.clothing.shoppingcarts.ui.shop.item.WomenClothItemViewModel
import com.clothing.shoppingcarts.utils.showCustomProgressBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BagFragment : BaseFragment(R.layout.fragment_bag) {

    private val binding: FragmentBagBinding by viewBinding()

    private val viewModel: BagViewModel by viewModels()

    @Inject
    lateinit var navManager: NavManager

    private var dialog: Dialog? = null

    // Observe ViewModel's state to take action on UI
    private val stateObserver = Observer<BagViewModel.ViewState> {

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

        createEpoxy()

        binding.ivBack.setOnDebouncedClickListener {
            findNavController().navigateUp()
        }

        binding.buyBtn.setOnDebouncedClickListener {
            val action = BagFragmentDirections.actionShopToCheckoutFragment()
            navManager.navigate(action)
        }
    }

    private fun createEpoxy(){
        binding.rvItems.withModels {
            if (view == null)
                return@withModels

            itemBagItems {
                id("bag_items")
                viewModel(viewModel)
                itemsAdd(object : BagInterface{
                    override fun onAdd() {
                        viewModel.onAdd()
                    }

                    override fun onRemove() {
                        if(viewModel.value.get()!!.toInt() != 0){
                            viewModel.onRemove()
                        }

                    }

                })
            }

            /*(1..2).forEachIndexed { index, i ->
                itemBagItems {
                    id("bag_items")
                    index(index)
                    viewModel(viewModel)
                    itemsAdd(object : BagInterface{
                        override fun onAdd(index: Int) {

                        }

                        override fun onRemove(index: Int) {

                        }

                    })
                }
            }*/

        }
    }
}