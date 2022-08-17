package com.clothing.shoppingcarts.ui.shop.womendetails

import com.clothing.shoppingcarts.network.responses.SubCategoryItem

interface DetailsClothListener {
    fun onClicked(item: SubCategoryItem)
    fun onShopLookClicked(index: Int)
}