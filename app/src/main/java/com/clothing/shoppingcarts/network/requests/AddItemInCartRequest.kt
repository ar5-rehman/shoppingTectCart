package com.clothing.shoppingcarts.network.requests

data class AddItemInCartRequest(
    val GarmentId: Int,
    val ColorwayId: Int,
    val Quantity: Int
)