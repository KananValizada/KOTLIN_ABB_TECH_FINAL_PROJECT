package com.example.food_mood.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.food_mood.R
import com.example.food_mood.data.entity.Food
import com.example.food_mood.databinding.FoodRowStagBinding
import com.example.food_mood.ui.fragment.MainFragmentDirections
import com.example.food_mood.ui.viewmodel.MainFragmentViewModel
import com.example.food_mood.utils.doNavigate
import com.example.food_mood.utils.showUrlImage

class FoodAdapter(var mContext:Context,
                  var foodList:List<Food>,
                  var viewModel: MainFragmentViewModel,
                  var currentUser: String): RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(binding: FoodRowStagBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding : FoodRowStagBinding
        init {
            this.binding = binding
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding : FoodRowStagBinding = DataBindingUtil.inflate(layoutInflater, R.layout.food_row_stag,parent,false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodList[position]
        val hb = holder.binding
        hb.foodObject = food
        hb.ivFoodImage.showUrlImage(food.image)
        hb.cvFood.setOnClickListener {
            Navigation.doNavigate(it,MainFragmentDirections.toDetail(food = food))
        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

}