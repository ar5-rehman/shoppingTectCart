package com.clothing.shoppingcarts.network.responses

data class AddItemInCartResponse(
    val CartDetails: List<CartDetail>,
    val CheckedOut: Boolean,
    val CreatedDate: String,
    val ExpireDate: String,
    val Id: Int,
    val ModifiedDate: String,
    val TotalAmount: Int,
    val UserProfileId: Int
)