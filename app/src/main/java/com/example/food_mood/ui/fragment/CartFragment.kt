package com.example.food_mood.ui.fragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.food_mood.R
import com.example.food_mood.databinding.FragmentCartBinding
import com.example.food_mood.ui.adapter.CartFoodAdapter
import com.example.food_mood.ui.viewmodel.CartFragmentViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding
    private lateinit var viewModel: CartFragmentViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUserEmail : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val tempViewModel : CartFragmentViewModel by viewModels()
        viewModel = tempViewModel
        currentUserEmail = auth.currentUser?.email.toString()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart,container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarCart)
        binding.toolbarCart.title = ""

        observeData()
        placeOrder()
        backPress()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCart(currentUserEmail)
        val cartTotalPrice = viewModel.getCartTotal()
        println(cartTotalPrice)
    }

    fun backPress() {
        binding.toolbarCart.setNavigationIcon(R.drawable.back_icon)
        binding.toolbarCart.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    fun observeData() {
        viewModel.cartFoodList.observe(viewLifecycleOwner){
            val adapter = CartFoodAdapter(requireContext(),it,viewModel)
            binding.cartFoodAdapter = adapter
            viewModel.getCartTotal()
            binding.tvCartTotalPrice.text = "Ümumi məbləğ: ${viewModel.getCartTotal().toString()} ₼"

            if (it.isEmpty()) {
                binding.tvCartTotalPrice.visibility = View.GONE
                binding.buttonPlaceOrder.visibility = View.GONE

            }else {
                binding.tvCartTotalPrice.visibility = View.VISIBLE
                binding.buttonPlaceOrder.visibility = View.VISIBLE

            }
        }
    }

    fun placeOrder() {
        binding.buttonPlaceOrder.setOnClickListener {
            viewModel.deleteAllCartFood(currentUserEmail)
            Handler().postDelayed({
                viewModel.getCart(currentUserEmail)
            }, 2500)
        }
    }

}