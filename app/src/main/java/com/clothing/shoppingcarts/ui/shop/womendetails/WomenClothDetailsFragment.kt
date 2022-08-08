package com.clothing.shoppingcarts.ui.shop.womendetails

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ar7lab.cherieapp.base.extension.observe
import com.clothing.shoppingcarts.R
import com.clothing.shoppingcarts.base.delegate.viewBinding
import com.clothing.shoppingcarts.base.extension.setOnDebouncedClickListener
import com.clothing.shoppingcarts.base.fragment.BaseFragment
import com.clothing.shoppingcarts.base.navigation.NavManager
import com.clothing.shoppingcarts.data.models.CategoriesDTOItem
import com.clothing.shoppingcarts.databinding.FragmentWomenClothDetailsBinding
import com.clothing.shoppingcarts.di.SharePreferencesManager
import com.clothing.shoppingcarts.itemWomenClothDetails
import com.clothing.shoppingcarts.network.responses.SubCategoryItem
import com.clothing.shoppingcarts.ui.shop.ShopFragmentDirections
import com.clothing.shoppingcarts.ui.shop.adapters.CategoriesAdapter
import com.clothing.shoppingcarts.utils.CATEGORY_ID
import com.clothing.shoppingcarts.utils.CATEGORY_NAME
import com.clothing.shoppingcarts.utils.RefreshDataListener
import com.clothing.shoppingcarts.utils.showCustomProgressBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WomenClothDetailsFragment : BaseFragment(R.layout.fragment_women_cloth_details),RefreshDataListener {

    private val binding: FragmentWomenClothDetailsBinding by viewBinding()

    @Inject
    lateinit var sharePreferencesManager: SharePreferencesManager

    @Inject
    lateinit var navManager: NavManager

    private val viewModel: WomenDetailsViewModel by viewModels()

    private var dialog: Dialog? = null
    private var categoryID: Int = -1
    private var categoryName: String = ""

    private var subCategoriesList: List<SubCategoryItem> = emptyList()

    // Observe ViewModel's state to take action on UI
    private val stateObserver = Observer<WomenDetailsViewModel.ViewState> {

        binding.isRefreshing = it.isRefreshing

        if(it.isError){
            dialog!!.dismiss()
            showError(it.message)
        }

        if(it.isLoading){
            dialog!!.show()
        }

        if(it.status){
            subCategoriesList = it.subCategories!!
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
            binding.tvTitle.text = categoryName
            viewModel.getSubCategories(categoryID)
        }

        createEpoxy()

        binding.ivBack.setOnDebouncedClickListener {
            findNavController().navigateUp()
        }

        binding.listener = this
    }

    private fun createEpoxy(){
        binding.rvItems.withModels {
            if (view == null)
                return@withModels

            if(subCategoriesList.isEmpty()){
                return@withModels
            }

            subCategoriesList.forEachIndexed { index, item ->
                itemWomenClothDetails {
                    id("details$index")
                    index(index)

                    item.defaultColorway.images.forEachIndexed { index, garmentImages ->
                        imageUrl("https://clothingtechshopping-api-dev.azurewebsites.net/Storage/GarmentImage/"+garmentImages.imageUrl)
                    }

                    name(item.name)
                    price("$"+item.price)
                    listener(object : DetailsClothListener{
                        override fun onClicked(index: Int) {
                            val action = WomenClothDetailsFragmentDirections.actionShopToWomenClothItemFragment(
                                subCategoriesList[index].price.toString(), categoryName, subCategoriesList[index].id)
                            navManager.navigate(action)
                        }
                    })
                }
            }
        }
    }

    override fun onRefresh() {
        viewModel.getSubCategories(categoryID)
    }
}