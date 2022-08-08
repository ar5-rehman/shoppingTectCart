package com.clothing.shoppingcarts.ui.shop.item

import androidx.lifecycle.viewModelScope
import com.clothing.shoppingcarts.base.viewmodel.BaseAction
import com.clothing.shoppingcarts.base.viewmodel.BaseViewModel
import com.clothing.shoppingcarts.base.viewmodel.BaseViewState
import com.clothing.shoppingcarts.data.models.CategoriesDTOItem
import com.clothing.shoppingcarts.di.SharePreferencesManager
import com.clothing.shoppingcarts.network.repositories.ClothingRepository
import com.clothing.shoppingcarts.network.responses.SubCategoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class WomenClothItemViewModel @Inject constructor(
    private val sharePreferencesManager: SharePreferencesManager,
    private val clothingRepository: ClothingRepository,
) : BaseViewModel<WomenClothItemViewModel.ViewState, WomenClothItemViewModel.Action>(ViewState()) {


    init {
        loadData()
    }

    fun getItem(id: Int) {
        viewModelScope.launch {
            try {
                sendAction(Action.LoadingStarting)
                val result = clothingRepository.getCategoryItem(id)
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

    fun onRefresh(){

    }

    // Define view state for Activity to use
    internal data class ViewState(
        val isError: Boolean = false,
        val message: String? = null,
        val isLoading: Boolean = false,
        val isSessionExpired: Boolean = false,
        val status: Boolean = false,
        val item: SubCategoryItem? = null,

    ) : BaseViewState

    // Define action of the ViewModel
    internal sealed class Action : BaseAction {
        object LoadingStarting : Action()
        class LoadingFailure(val message: String) : Action()
        class CategoriesSuccess(val status: Boolean, val item: SubCategoryItem) : Action()
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
        is Action.CategoriesSuccess -> state.copy(
            isLoading = false,
            isError = false,
            message = null,
            status = viewAction.status,
            item = viewAction.item
        )
    }
}