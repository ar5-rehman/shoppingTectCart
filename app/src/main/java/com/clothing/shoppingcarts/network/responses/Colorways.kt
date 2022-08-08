package com.clothing.shoppingcarts.network.responses

data class Colorways(
    val id: Int,
    val garmentId: Int,
    val color: String,
    val default: Boolean,
    val gdtId: String,
    val images: List<GarmentImages>,
    val createdDate: String,
    val modifiedDate: String,
)
