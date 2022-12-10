package com.example.food_mood.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.food_mood.data.entity.Food
import com.example.food_mood.data.repo.FoodDaoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodDetailsFragmentViewModel @Inject constructor(var frepo : FoodDaoRepo) : ViewModel(){

    fun addCart(name : String,
                image : String,
                price : Int,
                category:String,
                orderAmount : Int,
                userName : String){
        frepo.foodAddCart(name,image,price,category,orderAmount,userName)
    }

    fun addFoodToList(food: Food) {
        frepo.addCartFoodList.add(food)
    }
}