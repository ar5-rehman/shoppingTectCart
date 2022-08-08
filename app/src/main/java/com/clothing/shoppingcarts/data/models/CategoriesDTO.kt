package com.clothing.shoppingcarts.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CategoriesDTO : ArrayList<CategoriesDTOItem>()