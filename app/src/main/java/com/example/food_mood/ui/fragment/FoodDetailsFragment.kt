package com.example.food_mood.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.food_mood.R
import com.example.food_mood.data.entity.Food
import com.example.food_mood.databinding.FragmentFoodDetailsBinding
import com.example.food_mood.ui.viewmodel.FoodDetailsFragmentViewModel
import com.example.food_mood.utils.showUrlImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodDetailsFragment : Fragment() {
    private lateinit var binding : FragmentFoodDetailsBinding
    private lateinit var viewModel: FoodDetailsFragmentViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var getFood : Food

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val tempViewModel : FoodDetailsFragmentViewModel by viewModels()
        viewModel = tempViewModel
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_food_details,container,false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarFoodDetails)
//        binding.toolbarFoodDetails.setLogo(R.drawable.logofoodies)
        binding.toolbarFoodDetails.title = ""
        binding.azn = " ₼"
        binding.foodDetailsFragment = this
        val bundle : FoodDetailsFragmentArgs by navArgs()
        getFood = bundle.food
        binding.foodObject = getFood

        val currentUserEmail = auth.currentUser?.email

        binding.ivFoodImage.showUrlImage(getFood.image)
        buttonAddCart(currentUserEmail)
        countListener()
        backPress()
        return binding.root
    }

    private fun buttonAddCart(currentUserEmail : String?){
        binding.buttonAddCart.setOnClickListener {
            viewModel.addCart(getFood.name,getFood.image,getFood.price,getFood.category,getFood.foodCount,currentUserEmail!!)
            if (viewModel.frepo.success > 0) {
                Toast.makeText(context,"${getFood.name} sebətə əlavə edildi.", Toast.LENGTH_SHORT).show()
            }
            viewModel.addFoodToList(getFood) ///SONRADAN EKLENECEK
            println(getFood)
        }
    }

    private fun backPress(){
        binding.toolbarFoodDetails.setNavigationIcon(R.drawable.back_icon)
        binding.toolbarFoodDetails.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun countListener(){
        getFood.foodCount = 1
        binding.ivPlus.setOnClickListener {
            getFood.foodCount++
            println("ədəd = ${getFood.foodCount}")
            binding.tvFoodCount.text = getFood.foodCount.toString()
        }
        binding.ivMinus.setOnClickListener {
            if (getFood.foodCount > 1){
                getFood.foodCount--
                println(viewModel)
            }
            binding.tvFoodCount.text = getFood.foodCount.toString()
        }
    }

}