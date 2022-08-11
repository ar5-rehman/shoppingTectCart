package com.clothing.shoppingcarts.ui.shop.item

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.carousel
import com.ar7lab.cherieapp.base.extension.observe
import com.clothing.shoppingcarts.ItemWomenClothItemsBindingModel_
import com.clothing.shoppingcarts.R
import com.clothing.shoppingcarts.base.delegate.viewBinding
import com.clothing.shoppingcarts.base.extension.setOnDebouncedClickListener
import com.clothing.shoppingcarts.base.fragment.BaseFragment
import com.clothing.shoppingcarts.base.navigation.NavManager
import com.clothing.shoppingcarts.databinding.FragmentWomenClothDetailsBinding
import com.clothing.shoppingcarts.databinding.FragmentWomenClothItemBinding
import com.clothing.shoppingcarts.di.SharePreferencesManager
import com.clothing.shoppingcarts.itemWomenClothDetails
import com.clothing.shoppingcarts.itemWomenClothItemMainImg
import com.clothing.shoppingcarts.network.responses.SubCategoryItem
import com.clothing.shoppingcarts.ui.shop.womendetails.WomenClothDetailsFragmentDirections
import com.clothing.shoppingcarts.ui.unity.UnityActivity
import com.clothing.shoppingcarts.utils.CATEGORY_ID
import com.clothing.shoppingcarts.utils.CATEGORY_NAME
import com.clothing.shoppingcarts.utils.CATEGORY_PRICE
import com.clothing.shoppingcarts.utils.showCustomProgressBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WomenClothItemFragment : BaseFragment(R.layout.fragment_women_cloth_item) {

    private val binding: FragmentWomenClothItemBinding by viewBinding()

    @Inject
    lateinit var sharePreferencesManager: SharePreferencesManager

    private val viewModel: WomenClothItemViewModel by viewModels()

    @Inject
    lateinit var navManager: NavManager

    private var dialog: Dialog? = null
    private var categoryID: Int = -1
    private var categoryName: String = ""
    private var categoryPrice: String = ""
    private var colorwayID: String = ""

    private var item: SubCategoryItem? = null

    // Observe ViewModel's state to take action on UI
    private val stateObserver = Observer<WomenClothItemViewModel.ViewState> {

        if(it.isError){
            dialog!!.dismiss()
            showError(it.message)
        }

        if(it.isLoading){
            dialog!!.show()
        }

        if(it.status){
            item = it.item!!
            dialog!!.dismiss()

        }

        binding.rvItems.requestModelBuild()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.stateLiveData, stateObserver)

        dialog = showCustomProgressBar(requireContext(), layoutInflater)

        arguments?.let {
            categoryID = it.getInt(CATEGORY_ID)
            categoryName = it.getString(CATEGORY_NAME).toString()
            categoryPrice = it.getString(CATEGORY_PRICE).toString()
            binding.tvTitle.text = categoryName
            binding.tvDesc.text = getString(R.string.price_, categoryPrice)
        }

        viewModel.getItem(categoryID)

        createEpoxy()

        binding.ivBack.setOnDebouncedClickListener {
            findNavController().navigateUp()
        }
    }

    private fun createEpoxy(){
        binding.rvItems.withModels {
            if (view == null)
                return@withModels

            if(item == null){
                return@withModels
            }

            itemWomenClothItemMainImg {
                id("main_img")
                index(0)
                item!!.defaultColorway.images.forEachIndexed { index, garmentImages ->
                    imageUrl("https://clothingtechshopping-api-dev.azurewebsites.net/Storage/GarmentImage/"+garmentImages.imageUrl)
                }
                listener(object: ItemListener{
                    override fun onClicked(index: Int) {
                        val action = WomenClothItemFragmentDirections.actionShopToBagFragment()
                        navManager.navigate(action)
                    }
                })
                buyListener(object: BuyListener{
                    override fun onBuy(index: Int) {
                        val action = WomenClothItemFragmentDirections.actionShopToCheckoutFragment()
                        navManager.navigate(action)
                    }
                })
                tryListener(object: TryListener{
                    override fun onTry(index: Int) {
                        var vfrString: String = ("").toString()


                        val intent = Intent(requireActivity(), UnityActivity::class.java)
                        vfrString = vfrString + item!!.id.toString() + ";" + item!!.defaultColorway.id.toString() + ";"
                        intent.putExtra("vfrString", vfrString)
                        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                        startActivity(intent)
                    }
                })
            }

            val items = item!!.colorways.mapIndexed { index, item ->
                ItemWomenClothItemsBindingModel_()
                    .id("items$item")
                    .index(index)
                    .imageUrl("https://clothingtechshopping-api-dev.azurewebsites.net/Storage/GarmentImage/"+item.images[0].imageUrl)
                    .listener(object: ItemListener{
                        override fun onClicked(index: Int) {
                            colorwayID = item.id.toString()
                        }

                    })
            }

            carousel {
                id("items_carousel")
                models(items)
            }

        }
    }
}