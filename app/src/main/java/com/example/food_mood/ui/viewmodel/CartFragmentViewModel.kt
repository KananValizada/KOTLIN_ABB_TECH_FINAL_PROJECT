package com.example.food_mood.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food_mood.data.entity.CartFood
import com.example.food_mood.data.repo.FoodDaoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CartFragmentViewModel @Inject constructor(var frepo : FoodDaoRepo) : ViewModel() {
    var cartFoodList = MutableLiveData<List<CartFood>>()

    init {
        cartFoodList = frepo.getCartFoods()
    }

    fun getCart(userName : String){
        frepo.getCartFood(userName)
    }

    fun deleteFood(id : Int,userName : String){
        frepo.deleteCartFood(id,userName)
    }

    fun getCartTotal(): Double {
        var total = 0.00
        cartFoodList.value?.forEach {
            total += (it.price * it.orderAmount).toDouble()
        }
        return total
    }

    private fun getCartFoodID(): ArrayList<Int> {
        val idList  = ArrayList<Int>()
        cartFoodList.value?.forEach {
            idList.add(it.cartId)
        }
        return idList
    }

    fun deleteAllCartFood(kullanici_adi : String) {
        val idList = getCartFoodID()
        idList.forEach {
            Log.e("Cart ID: ", "$idList")
            frepo.deleteCartFood(it, kullanici_adi)
        }
    }


}