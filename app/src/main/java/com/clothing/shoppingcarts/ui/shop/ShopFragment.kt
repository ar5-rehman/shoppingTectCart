package com.clothing.shoppingcarts.ui.shop

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ar7lab.cherieapp.base.extension.observe
import com.clothing.shoppingcarts.R
import com.clothing.shoppingcarts.base.delegate.viewBinding
import com.clothing.shoppingcarts.base.enums.CategoryEnum
import com.clothing.shoppingcarts.base.fragment.BaseFragment
import com.clothing.shoppingcarts.base.navigation.NavManager
import com.clothing.shoppingcarts.data.models.CategoriesDTOItem
import com.clothing.shoppingcarts.databinding.FragmentShopBinding
import com.clothing.shoppingcarts.di.SharePreferencesManager
import com.clothing.shoppingcarts.ui.shop.adapters.CategoriesAdapter
import com.clothing.shoppingcarts.utils.showCustomProgressBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShopFragment : BaseFragment(R.layout.fragment_shop) {

    private val binding: FragmentShopBinding by viewBinding()

    private val viewModel: ShopCategoriesViewModel by viewModels()

    @Inject
    lateinit var sharePreferencesManager: SharePreferencesManager

    @Inject
    lateinit var navManager: NavManager

    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var categoriesAdapter: CategoriesAdapter
    private var categoriesList: List<CategoriesDTOItem> = emptyList()

    private var dialog: Dialog? = null

    // Observe ViewModel's state to take action on UI
    private val stateObserver = Observer<ShopCategoriesViewModel.ViewState> {

        if(it.isError){
            dialog!!.dismiss()
            showError(it.message)
        }

        when (it.typeSelected) {
            CategoryEnum.WOMEN -> {

                binding.tvWomen.typeface = ResourcesCompat.getFont(context!!, R.font.sf_pro_text_semi_bold)
                binding.tvMen.typeface = ResourcesCompat.getFont(context!!, R.font.sf_pro_text_regular)
            }
            CategoryEnum.MEN -> {

                binding.tvWomen.typeface = ResourcesCompat.getFont(context!!, R.font.sf_pro_text_regular)
                binding.tvMen.typeface = ResourcesCompat.getFont(context!!, R.font.sf_pro_text_semi_bold)
            }
            else -> {}
        }

        if(it.status){
            categoriesList = it.categories!!

            //categoryDTOS recycler
            binding.rvCategories.setHasFixedSize(true)
            gridLayoutManager = GridLayoutManager(context, 2)
            binding.rvCategories.layoutManager = gridLayoutManager
            categoriesAdapter = CategoriesAdapter(requireContext(), categoriesList[1].categories, navManager)
            binding.rvCategories.adapter = categoriesAdapter
            categoriesAdapter.setCategoriesData(categoriesList[1].categories)

            dialog!!.dismiss()

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        observe(viewModel.stateLiveData, stateObserver)

        binding.tvWomen.typeface = ResourcesCompat.getFont(context!!, R.font.sf_pro_text_semi_bold)
        binding.tvMen.typeface = ResourcesCompat.getFont(context!!, R.font.sf_pro_text_regular)

        dialog = showCustomProgressBar(requireContext(), layoutInflater)

        binding.ivClose.setOnClickListener {
            binding.promoDialogLayout.isGone = true
            binding.promoBgImage.isGone = true
            binding.mainLayout.isVisible = true

            if(categoriesList.isEmpty()){
                dialog!!.show()
                viewModel.getCategories()
            }else{
                //categoryDTOS recycler
                binding.rvCategories.setHasFixedSize(true)
                gridLayoutManager = GridLayoutManager(context, 2)
                binding.rvCategories.layoutManager = gridLayoutManager
                categoriesAdapter = CategoriesAdapter(requireContext(), categoriesList[1].categories, navManager)
                binding.rvCategories.adapter = categoriesAdapter
                categoriesAdapter.setCategoriesData(categoriesList[1].categories)

                if(dialog!!.isShowing){
                    dialog!!.dismiss()
                }
            }

            viewModel.onSelectType(CategoryEnum.WOMEN)
        }

        binding.tvNoThanks.setOnClickListener {
            binding.promoDialogLayout.isGone = true
            binding.promoBgImage.isGone = true
            binding.mainLayout.isVisible = true

            if(categoriesList.isEmpty()){
                dialog!!.show()
                viewModel.getCategories()
            }else{
                //categoryDTOS recycler
                binding.rvCategories.setHasFixedSize(true)
                gridLayoutManager = GridLayoutManager(context, 2)
                binding.rvCategories.layoutManager = gridLayoutManager
                categoriesAdapter = CategoriesAdapter(requireContext(), categoriesList[1].categories, navManager)
                binding.rvCategories.adapter = categoriesAdapter
                categoriesAdapter.setCategoriesData(categoriesList[1].categories)

                if(dialog!!.isShowing){
                    dialog!!.dismiss()
                }
            }

            viewModel.onSelectType(CategoryEnum.WOMEN)
        }

    }
}