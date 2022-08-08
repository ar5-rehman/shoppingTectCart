package com.clothing.shoppingcarts.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoriesClothingDetail(
    val createdDate: String,
    val id: Int,
    val name: String,
    val parentCategoryId: Int
)