package com.clothing.shoppingcarts.network.repositories

import com.clothing.shoppingcarts.data.models.CategoriesDTOItem
import com.clothing.shoppingcarts.network.requests.AddItemInCartRequest
import com.clothing.shoppingcarts.network.responses.AddItemInCartResponse
import com.clothing.shoppingcarts.network.responses.SubCategoryItem
import com.clothing.shoppingcarts.network.services.ClothingService

class ClothingRepositoryImpl(private val service: ClothingService) : ClothingRepository {

    override suspend fun getCategories(): List<CategoriesDTOItem> {
        return service.getCategories()
    }

    override suspend fun getSubCategories(id: Int): List<SubCategoryItem> {
        return service.getSubCategories(id)
    }

    override suspend fun getCategoryItem(id: Int): SubCategoryItem {
        return service.categoryItem(id)
    }

    override suspend fun addItemInCart(
        garmentId: Int,
        colorwayId: Int,
        quantity: Int
    ): AddItemInCartResponse {
        return service.addItemsInCart(AddItemInCartRequest(garmentId, colorwayId, quantity))
    }

}