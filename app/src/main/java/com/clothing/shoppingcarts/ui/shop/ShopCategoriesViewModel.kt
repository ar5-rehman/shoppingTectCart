package com.clothing.shoppingcarts.ui.shop

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.clothing.shoppingcarts.base.enums.CategoryEnum
import com.clothing.shoppingcarts.base.viewmodel.BaseAction
import com.clothing.shoppingcarts.base.viewmodel.BaseViewModel
import com.clothing.shoppingcarts.base.viewmodel.BaseViewState
import com.clothing.shoppingcarts.data.models.CategoriesDTO
import com.clothing.shoppingcarts.data.models.CategoriesDTOItem
import com.clothing.shoppingcarts.di.SharePreferencesManager
import com.clothing.shoppingcarts.network.repositories.ClothingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
internal class ShopCategoriesViewModel @Inject constructor(
    private val sharePreferencesManager: SharePreferencesManager,
    private val clothingRepository: ClothingRepository,
) : BaseViewModel<ShopCategoriesViewModel.ViewState, ShopCategoriesViewModel.Action>(ViewState()) {


    val typeSelected = ObservableField(CategoryEnum.NON)


    init {
        loadData()
    }

    fun getCategories() {
        viewModelScope.launch {
            try {
                sendAction(Action.LoadingStarting)
                val result = clothingRepository.getCategories()
                sendAction(Action.CategoriesSuccess(true, result))
            } catch (e: Exception) {
                val error = getErrorMessage(e)
                sendAction(Action.LoadingFailure(error.message))
            }
        }
    }

    /**
     * Loading data from API
     *
     */
    override fun onLoadData() {
        //load data from API
    }

    //On click on any Type
    fun onSelectType(categoryEnum: CategoryEnum) {
        if (categoryEnum == typeSelected.get()) return
        typeSelected.set(categoryEnum)
        sendAction(Action.TypeChanged(categoryEnum))
    }


    // Define view state for Activity to use
    internal data class ViewState(
        val isError: Boolean = false,
        val message: String? = null,
        val isLoading: Boolean = false,
        val isSessionExpired: Boolean = false,
        val status: Boolean = false,
        val categories: List<CategoriesDTOItem>? = null,
        val typeSelected: CategoryEnum = CategoryEnum.NON,

    ) : BaseViewState

    // Define action of the ViewModel
    internal sealed class Action : BaseAction {
        object LoadingStarting : Action()
        class LoadingFailure(val message: String) : Action()
        class TypeChanged(val categoryEnum: CategoryEnum) : Action()
        class CategoriesSuccess(val status: Boolean, val categories: List<CategoriesDTOItem>) : Action()
    }


    // Return a state based on action
    override fun onReduceState(viewAction: Action): ViewState = when (viewAction) {
        is Action.LoadingFailure -> state.copy(
            isError = true,
            message = viewAction.message,
            status = false,
            isLoading = false
        )
        is Action.LoadingStarting -> state.copy(
            isLoading = true,
            isError = false,
            status = false,
            message = null,
        )

        is Action.TypeChanged -> state.copy(
            typeSelected = viewAction.categoryEnum,
            isError = false,
            message = null,
            status = false,
            isLoading = false
        )
        is Action.CategoriesSuccess -> state.copy(
            isLoading = false,
            isError = false,
            message = null,
            status = viewAction.status,
            categories = viewAction.categories
        )
    }
}