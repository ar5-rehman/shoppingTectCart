package com.clothing.shoppingcarts.network.responses

class SubCategoryItem(
    val id: Int,
    val price: Double,
    val name: String,
    val isActive: Boolean,
    val gdtId: String,
    var shopLookVisibility: Boolean = false,
    val defaultColorway: DefaultColorway,
    val colorways: List<DefaultColorway>,
)
