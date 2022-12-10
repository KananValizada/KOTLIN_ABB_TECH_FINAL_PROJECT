package com.example.food_mood.retrofit

import com.example.food_mood.data.entity.CartFoodResponse
import com.example.food_mood.data.entity.FoodResponce
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodDao {
    @GET("foods/getAllFoods.php")
    fun getAllFoods() : Call<FoodResponce>

    @POST("foods/insertFood.php")
    @FormUrlEncoded
    fun foodAddCart(@Field ("name") name : String,
                    @Field ("image") image : String,
                    @Field ("price") price : Int,
                    @Field ("category") category : String,
                    @Field ("orderAmount") orderAmount : Int,
                    @Field ("userName") userName : String) : Call<CRUDResponse>

    @POST("foods/getFoodsCart.php")
    @FormUrlEncoded
    fun getCartFoods(@Field("userName") userName : String) : Call<CartFoodResponse>

    @POST("foods/deleteFood.php")
    @FormUrlEncoded
    fun deleteCartFood(@Field ("cartId") cartId : Int,
                       @Field ("userName") userName : String) : Call<CRUDResponse>
}