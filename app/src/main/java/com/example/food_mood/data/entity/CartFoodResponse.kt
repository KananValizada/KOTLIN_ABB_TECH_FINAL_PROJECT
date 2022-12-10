package com.example.food_mood.data.entity

import com.google.gson.annotations.SerializedName

data class CartFoodResponse(@SerializedName("foods_cart") var foods_cart : List<CartFood>,
                            @SerializedName("success") var success : Int) {
}