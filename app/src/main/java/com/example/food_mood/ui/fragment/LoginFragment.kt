package com.example.food_mood.ui.fragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.food_mood.R
import com.example.food_mood.databinding.FragmentLoginBinding
import com.example.food_mood.utils.doNavigate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

 override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(inflater,container,false)

     val currentUser = auth.currentUser
     if(currentUser != null){
         findNavController().navigate(LoginFragmentDirections.toMain())
     }

     signIn()
     signUp()


        return binding.root
    }

    private fun signUp(){
        binding.buttonSignup.setOnClickListener {
            val view = it
            val email = binding.textEmailAdress.editText?.text.toString()
            val password = binding.textPassword.editText?.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(requireContext(),"E-poçt və şifrə boş olmamalıdır!", Toast.LENGTH_SHORT).show()
            }else{
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Hesabınız uğurla yaradıldı.",
                            Toast.LENGTH_SHORT).show()
                        Navigation.doNavigate(view,LoginFragmentDirections.toMain())
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), it.localizedMessage,
                            Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private fun signIn(){
        binding.buttonSignin.setOnClickListener {
            val view = it
            val email = binding.textEmailAdress.editText?.text.toString()
            val password = binding.textPassword.editText?.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(requireContext(),"E-poçt və şifrə boş olmamalıdır!", Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Uğurla daxil oldunuz.",
                            Toast.LENGTH_SHORT).show()
                        Navigation.doNavigate(view,LoginFragmentDirections.toMain())
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(),it.localizedMessage, Toast.LENGTH_LONG).show()
                    }
            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Proqramdan çıxmaq istədiyinizə əminsiniz mi?")
                    builder.setMessage("Yeməklərə baxmaq istəyirəm?")
                    builder.setPositiveButton("Bəli",
                        DialogInterface.OnClickListener { dialog, which ->
                            android.os.Process.killProcess(android.os.Process.myPid())
                        })
                    builder.setNegativeButton("Xeyr",
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