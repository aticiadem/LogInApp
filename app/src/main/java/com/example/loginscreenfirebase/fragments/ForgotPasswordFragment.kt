package com.example.loginscreenfirebase.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.loginscreenfirebase.R
import com.example.loginscreenfirebase.databinding.FragmentForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding.buttonSendMeEmail.setOnClickListener {
            if (binding.editTextMail.text.isNotEmpty()){
                val mail = binding.editTextMail.text.toString()
                auth.sendPasswordResetEmail(mail).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(activity,"Mail Gönderildi.",Toast.LENGTH_SHORT).show()
                        val action = ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToMainFragment()
                        Navigation.findNavController(it).navigate(action)
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(activity,exception.localizedMessage,Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity,"Mail Adresinizi Girin!",Toast.LENGTH_SHORT).show()
            }
        }
    }

}