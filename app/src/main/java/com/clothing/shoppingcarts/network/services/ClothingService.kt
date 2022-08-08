package com.clothing.shoppingcarts.network.services

import com.clothing.shoppingcarts.data.models.CategoriesDTOItem
import com.clothing.shoppingcarts.network.requests.AddItemInCartRequest
import com.clothing.shoppingcarts.network.responses.AddItemInCartResponse
import com.clothing.shoppingcarts.network.responses.SubCategoryItem
import retrofit2.http.*

interface ClothingService {

    @GET("Categories")
    suspend fun getCategories(): List<CategoriesDTOItem>

    @GET("Categories/{id}/Garments")
    suspend fun getSubCategories(@Path("id") id : Int): List<SubCategoryItem>

    @GET("Garment/{garmentId}")
    suspend fun categoryItem(@Path("garmentId") id : Int): SubCategoryItem

    @POST("Cart/Item")
    suspend fun addItemsInCart(@Body addItemInCartRequest: AddItemInCartRequest): AddItemInCartResponse

    @GET("Cart/")
    suspend fun getCartItems(): AddItemInCartResponse

}