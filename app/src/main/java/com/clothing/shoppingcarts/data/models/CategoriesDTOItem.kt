package com.clothing.shoppingcarts.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoriesDTOItem(
    val categories: List<Category>,
    val createdDate: String,
    val id: Int,
    val name: String,
)