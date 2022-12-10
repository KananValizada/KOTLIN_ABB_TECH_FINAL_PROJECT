package com.example.food_mood.retrofit

import com.google.gson.annotations.SerializedName

data class CRUDResponse(@SerializedName("success") var success:Int,
                        @SerializedName("message") var message:String) {
}