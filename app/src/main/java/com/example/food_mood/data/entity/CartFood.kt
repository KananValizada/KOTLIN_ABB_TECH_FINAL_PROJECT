package com.example.food_mood.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CartFood(
    @SerializedName("cartId") var cartId: Int,
    @SerializedName("name") var name: String,
    @SerializedName("image") var image: String,
    @SerializedName("price") var price: Int,
    @SerializedName("category") var category: String,
    @SerializedName("orderAmount") var orderAmount: Int,
    @SerializedName("userName") var userName: String
) : Serializable {
}