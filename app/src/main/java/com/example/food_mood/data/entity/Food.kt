package com.example.food_mood.data.entity

import com.google.gson.annotations.SerializedName

data class Food(@SerializedName("id") var id:Int,
                @SerializedName("name")var name:String,
                @SerializedName("image")var image:String,
                @SerializedName("price")var price:Int,
                @SerializedName("category")var category:String,
                var foodCount:Int = 0):java.io.Serializable {
}