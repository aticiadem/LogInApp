package com.example.loginscreenfirebase.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.loginscreenfirebase.R
import com.example.loginscreenfirebase.databinding.FragmentMainBinding
import com.example.loginscreenfirebase.view.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding.textViewNewUser.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToSignUpFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.textViewForgotPassword.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToForgotPasswordFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.buttonSignIn.setOnClickListener {
            if (binding.editTextMail.text.isNotEmpty() && binding.editTextPassword.text.isNotEmpty()){
                val email =  binding.editTextMail.text.toString()
                val password = binding.editTextPassword.text.toString()

                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            activity?.let{
                                Toast.makeText(activity,"Hoş Geldiniz",Toast.LENGTH_SHORT).show()
                                val intent = Intent (it, WelcomeActivity::class.java)
                                it.startActivity(intent)
                            }
                            /*requireActivity().run {
                                startActivity(Intent(this, WelcomeActivity::class.java))
                                finish()
                            }*/
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(activity,exception.localizedMessage,Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(activity,"Lütfen Bütün Alanları Doldurun.",Toast.LENGTH_SHORT).show()
            }
        }

    }

}