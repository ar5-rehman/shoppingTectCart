package com.clothing.shoppingcarts.network.responses

data class CartDetail(
    val CartId: Int,
    val CreatedDate: String,
    val GarmentId: Int,
    val GarmentName: String,
    val GarmentPrice: Int,
    val Id: Int,
    val ModifiedDate: String,
    val Quantity: Int
)