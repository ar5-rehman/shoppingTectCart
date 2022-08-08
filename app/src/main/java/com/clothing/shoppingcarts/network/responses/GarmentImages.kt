package com.clothing.shoppingcarts.network.responses

data class GarmentImages(
    val id: Int,
    val garmentColorwayId: Int,
    val name: String,
    val alternateText: String,
    val imageUrl: String,
    val main: Boolean,
    val createdDate: String
)