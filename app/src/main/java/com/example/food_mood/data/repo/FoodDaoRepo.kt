package com.example.food_mood.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.food_mood.data.entity.CartFood
import com.example.food_mood.data.entity.CartFoodResponse
import com.example.food_mood.data.entity.Food
import com.example.food_mood.data.entity.FoodResponce
import com.example.food_mood.retrofit.CRUDResponse
import com.example.food_mood.retrofit.FoodDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class FoodDaoRepo @Inject constructor(var fdao:FoodDao) {
    var foodList : MutableLiveData<List<Food>> = MutableLiveData()
    var cartFoodList : MutableLiveData<List<CartFood>> = MutableLiveData()
    var addCartFoodList : ArrayList<Food> = ArrayList()
    var success : Int = 0

    fun getFood() : MutableLiveData<List<Food>>{
        return foodList
    }

    fun getAllFoods() {
        Log.e("getAllFoods", "has been called.")
        fdao.getAllFoods().enqueue(object : Callback<FoodResponce> {
            override fun onResponse(call: Call<FoodResponce>?, response: Response<FoodResponce>) {
                Log.e("getAllFoods", "onResponse")
                val list = response.body()!!.foodList
                foodList.value = list
            }
            override fun onFailure(call: Call<FoodResponce>?, t: Throwable?) {
                Log.e("getAllFoods", "onFailure")
                println("$t.localizedMessage")
            }
        })
    }

    fun getCartFoods(): MutableLiveData<List<CartFood>> {
        return cartFoodList
    }


    fun getCartFood(userName : String){
        fdao.getCartFoods(userName).enqueue(object : Callback<CartFoodResponse>{
            override fun onResponse(
                call: Call<CartFoodResponse>?,
                response: Response<CartFoodResponse>
            ) {
                val list = response.body()?.foods_cart
                cartFoodList.value = list!!
            }

            override fun onFailure(call: Call<CartFoodResponse>?, t: Throwable?) {
                cartFoodList.value = emptyList()
            }
        })
    }

    fun foodAddCart(name : String,image : String,price : Int,category:String,orderAmount : Int,userName : String){
        fdao.foodAddCart(name,image,price,category,orderAmount,userName).enqueue(object : Callback<CRUDResponse>{
            override fun onResponse(call: Call<CRUDResponse>?, response: Response<CRUDResponse>) {
                success = response.body()?.success!!
                val message = response.body()?.message
                Log.e("foodAddCart", "Success: $success - $message" )
            }

            override fun onFailure(call: Call<CRUDResponse>?, t: Throwable?) {
                println("foodAddCart onFailure")
                println(t?.localizedMessage)
            }
        })
    }

    fun deleteCartFood(cartId : Int,userName : String){
        fdao.deleteCartFood(cartId,userName).enqueue(object : Callback<CRUDResponse>{
            override fun onResponse(call: Call<CRUDResponse>?, response: Response<CRUDResponse>) {
                val success = response.body()?.success
                val message = response.body()?.message
                Log.e("Delete Food", " $success - $message")
            }

            override fun onFailure(call: Call<CRUDResponse>?, t: Throwable?) {
                println("deleteCartFood onFailure")
                println(t?.localizedMessage)
            }

        })
    }

}