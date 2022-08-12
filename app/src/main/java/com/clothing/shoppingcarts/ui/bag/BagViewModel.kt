package com.clothing.shoppingcarts.ui.bag

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.clothing.shoppingcarts.base.viewmodel.BaseAction
import com.clothing.shoppingcarts.base.viewmodel.BaseViewModel
import com.clothing.shoppingcarts.base.viewmodel.BaseViewState
import com.clothing.shoppingcarts.data.models.CategoriesDTOItem
import com.clothing.shoppingcarts.di.SharePreferencesManager
import com.clothing.shoppingcarts.network.repositories.ClothingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class BagViewModel @Inject constructor(
    private val sharePreferencesManager: SharePreferencesManager,
    private val clothingRepository: ClothingRepository,
) : BaseViewModel<BagViewModel.ViewState, BagViewModel.Action>(ViewState()) {

    val value = ObservableField("1")

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

    fun onAdd(){
        var v = value.get()!!.toInt()
        v++
        value.set(v.toString())
    }

    fun onRemove(){
        var v = value.get()!!.toInt()
        v--
        value.set(v.toString())
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
        val categories: List<CategoriesDTOItem>? = null,

    ) : BaseViewState

    // Define action of the ViewModel
    internal sealed class Action : BaseAction {
        object LoadingStarting : Action()
        class LoadingFailure(val message: String) : Action()
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
        is Action.CategoriesSuccess -> state.copy(
            isLoading = false,
            isError = false,
            message = null,
            status = viewAction.status,
            categories = viewAction.categories
        )
    }
}