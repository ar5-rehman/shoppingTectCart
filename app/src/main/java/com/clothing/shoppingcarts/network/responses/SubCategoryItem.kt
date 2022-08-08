package com.clothing.shoppingcarts.network.responses

data class SubCategoryItem(
    val id: Int,
    val price: Double,
    val name: String,
    val isActive: Boolean,
    val gdtId: String,
    val defaultColorway: DefaultColorway,
    val colorways: List<DefaultColorway>,
)
