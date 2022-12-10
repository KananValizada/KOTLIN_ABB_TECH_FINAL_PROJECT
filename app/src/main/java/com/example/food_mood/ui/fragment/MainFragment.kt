package com.example.food_mood.ui.fragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.food_mood.R
import com.example.food_mood.databinding.FragmentMainBindingImpl
import com.example.food_mood.ui.viewmodel.MainFragmentViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.food_mood.data.entity.Food
import com.example.food_mood.databinding.FragmentMainBinding
import com.example.food_mood.ui.adapter.FoodAdapter
import com.example.food_mood.utils.Status
import com.example.food_mood.utils.doNavigate
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainFragment : Fragment(),SearchView.OnQueryTextListener {
    private lateinit var binding : FragmentMainBinding
    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUserEmail : String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setHasOptionsMenu(true)
        val tempViewModel: MainFragmentViewModel by viewModels()
        viewModel = tempViewModel

        currentUserEmail = auth.currentUser?.email.toString()
        viewModel.getCart(currentUserEmail)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        binding.toolbarMainTitle = ""
        binding.toolbarMain.setLogo(R.drawable.foodmood2)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarMain)


        viewModel.foodList.observe(viewLifecycleOwner){
            println(it)
            val adapter = FoodAdapter(requireContext(), it,viewModel, currentUserEmail)
            binding.foodAdapter = adapter
        }
        getDataFromAPI()

        binding.fabCart.setOnClickListener {
            Navigation.doNavigate(it,MainFragmentDirections.toCart())
        }

        val menuHost: MenuHost = requireActivity()
        requireActivity().addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu,menu)

                val item = menu.findItem(R.id.action_search)
                val searchView = item.actionView as SearchView
                searchView.setOnQueryTextListener(this@MainFragment)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        },viewLifecycleOwner,Lifecycle.State.RESUMED)





        return binding.root
    }




//    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//        menuInflater.inflate(R.menu.main_menu,menu)
//        val item = menu.findItem(R.id.action_search)
//        val searchView = item.actionView as androidx.appcompat.widget.SearchView
//        searchView.setOnQueryTextListener(this)
//    }
//
//    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//        // Do stuff...
//
//        if (menuItem.itemId == R.id.action_signout){
//            auth.signOut()
//            findNavController().navigate(MainFragmentDirections.actionMainFragmentToLoginFragment())
//        }
//        return super.onOptionsItemSelected(menuItem)
//    }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_signout){
            auth.signOut()
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToLoginFragment())
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
    }


    private fun getDataFromAPI() {
        viewModel.downloadFoods().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("STATUS", "SUCCESS")
                    binding.rvFood.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    Log.e("STATUS", "LOADING")
                    binding.rvFood.visibility = View.GONE
                }
                Status.ERROR -> {
                    Log.e("STATUS", "ERROR")
                    binding.rvFood.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        println("onQueryTextSubmit")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        println("onQueryTextChange")
        val filteredlist: ArrayList<Food> = ArrayList()
        for (item in viewModel.foodList.value!!) {
            if (item.name.lowercase(Locale.ROOT).contains(newText!!))
                filteredlist.add(item)
        }
        val adapter = FoodAdapter(requireContext(), filteredlist,viewModel, currentUserEmail)
        binding.foodAdapter = adapter
        return true
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Proqramdan çıxmaq istədiyinizə əminsiniz mi?")
                    builder.setMessage("Yeməklərə baxmaq istəyirəm?")
                    builder.setPositiveButton("bəli",
                        DialogInterface.OnClickListener { dialog, which ->
                            android.os.Process.killProcess(android.os.Process.myPid())
                        })
                    builder.setNegativeButton("XEYİR",
                        DialogInterface.OnClickListener { dialog, which ->
                        })
                    builder.show()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

}