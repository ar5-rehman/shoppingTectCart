package com.clothing.shoppingcarts.network.repositories

import com.clothing.shoppingcarts.data.models.CategoriesDTOItem
import com.clothing.shoppingcarts.network.responses.AddItemInCartResponse
import com.clothing.shoppingcarts.network.responses.SubCategoryItem

interface ClothingRepository {
    suspend fun getCategories(): List<CategoriesDTOItem>

    suspend fun getSubCategories(id: Int): List<SubCategoryItem>

    suspend fun getCategoryItem(id: Int): SubCategoryItem

    suspend fun addItemInCart(garmentId: Int, colorwayId: Int, quantity: Int): AddItemInCartResponse
}