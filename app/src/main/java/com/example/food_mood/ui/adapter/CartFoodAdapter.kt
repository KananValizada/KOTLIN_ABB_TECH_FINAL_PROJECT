package com.example.food_mood.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.food_mood.R
import com.example.food_mood.data.entity.CartFood
import com.example.food_mood.databinding.CartFoodsBinding
import com.example.food_mood.ui.viewmodel.CartFragmentViewModel
import com.example.food_mood.utils.showUrlImage

class CartFoodAdapter(var mContext: Context,
                      var cartFoodList:List<CartFood>,
                      var viewModel: CartFragmentViewModel
): RecyclerView.Adapter<CartFoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(binding: CartFoodsBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding : CartFoodsBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding : CartFoodsBinding = DataBindingUtil.inflate(layoutInflater, R.layout.cart_foods,parent,false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = cartFoodList[position]
        val hb = holder.binding
        hb.cartFood = food
        hb.azn = " ₼"
        hb.ivFoodImage.showUrlImage(food.image)


        hb.ivDeleteFood.setOnClickListener {
            val builder = AlertDialog.Builder(mContext)
            builder.setTitle("Səbətdən çıxarılsın?")
            builder.setMessage("${food.name} səbətdən çıxarılacaq.")
            builder.setPositiveButton("bəli", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(mContext,"${food.name} səbətdən çıxarıldı.", Toast.LENGTH_SHORT).show()
                viewModel.deleteFood(food.cartId,food.userName)
                Log.e("Çıxarılan yemək :", "${food.cartId} -  ${food.userName}")
                viewModel.getCart(food.userName)
            })
            builder.setNegativeButton("XEYiR", DialogInterface.OnClickListener { dialog, which ->
            })
            builder.show()

        }
    }

    override fun getItemCount(): Int {
        return cartFoodList.size
    }

}