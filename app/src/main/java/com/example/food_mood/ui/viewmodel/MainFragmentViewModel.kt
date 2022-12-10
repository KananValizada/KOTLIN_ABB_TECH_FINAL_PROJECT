package com.example.food_mood.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.food_mood.data.entity.CartFood
import com.example.food_mood.data.entity.Food
import com.example.food_mood.data.repo.FoodDaoRepo
import com.example.food_mood.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(var frepo : FoodDaoRepo): ViewModel()  {
    var foodList = MutableLiveData<List<Food>>()
    var cartFoodList = MutableLiveData<List<CartFood>>()

    init {
        downloadFoods()
        foodList = frepo.getFood()
        cartFoodList = frepo.getCartFoods()

    }

    fun downloadFoods() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = frepo.getAllFoods()))
        } catch (exception: Exception) {
            emit(Resource.error(exception.message ?: "Error !!!", data = null))
        }
    }


    fun getCart(userName : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = frepo.getCartFood(userName)))
        } catch (exception: Exception) {
            emit(Resource.error(exception.message ?: "Error !!!", data = null))
        }
    }
}