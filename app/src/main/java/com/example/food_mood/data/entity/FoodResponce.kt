package com.example.food_mood.data.entity

import com.google.gson.annotations.SerializedName

data class FoodResponce (@SerializedName("foods") var foodList : List<Food>,
                    @SerializedName("success") var success : Int) {
}

