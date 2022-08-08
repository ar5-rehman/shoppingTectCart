package com.clothing.shoppingcarts.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(
    val categories: List<CategoriesClothingDetail>,
    val createdDate: String,
    val id: Int,
    //val modifiedDate: Any,
    val name: String,
    val parentCategoryId: Int
)