package com.example.food_mood.di

import com.example.food_mood.data.repo.FoodDaoRepo
import com.example.food_mood.retrofit.ApiUtils
import com.example.food_mood.retrofit.FoodDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideFoodDaoRepo(fdao : FoodDao) : FoodDaoRepo {
        return FoodDaoRepo(fdao)
    }

    @Provides
    @Singleton
    fun provideFoodDao() : FoodDao{
        return ApiUtils.getFoodDao()
    }
}