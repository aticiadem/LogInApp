package com.example.loginscreenfirebase.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.loginscreenfirebase.R
import com.example.loginscreenfirebase.databinding.FragmentSignUpBinding
import com.example.loginscreenfirebase.view.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding.buttonSignUp.setOnClickListener {
            if (binding.editTextMail.text.isNotEmpty() && binding.editTextPassword.text.isNotEmpty()
                && binding.editTextMail.text.isNotEmpty()) {
                val mail = binding.editTextMail.text.toString()
                val pwd = binding.editTextPassword.text.toString()
                val pwdAgain = binding.editTextPasswordAgain.text.toString()
                if (pwd == pwdAgain) {
                    auth.createUserWithEmailAndPassword(mail,pwd)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                /*if(binding.editTextUsername.text.isNotEmpty()){
                                    updateProfile()
                                }*/
                                Toast.makeText(activity,"Kayıt Başarılı!",Toast.LENGTH_SHORT).show()
                                requireActivity().run {
                                    startActivity(Intent(this, WelcomeActivity::class.java))
                                    finish()
                                }
                            }
                        }.addOnFailureListener { exception ->
                            Toast.makeText(activity,exception.localizedMessage,Toast.LENGTH_SHORT).show()
                        }
                } else{
                    Toast.makeText(activity,"Şifreler Aynı Olmalı",Toast.LENGTH_SHORT) .show()
                }
            } else {
                Toast.makeText(activity,"Lütfen Bütün Alanları Doldurunuz",Toast.LENGTH_SHORT) .show()
            }
        }
    }

    /*fun updateProfile(){
        val user = Firebase.auth.currentUser
        val profileUpdates = userProfileChangeRequest {
            displayName = binding.editTextUsername.text.toString()
        }
        user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { taskusername ->
                    if (taskusername.isSuccessful) {
                        Toast.makeText(activity,"Kullanıcı Adı Kaydı Başarılı!",Toast.LENGTH_SHORT).show()
                    }
                } .addOnFailureListener { exception ->
                    Toast.makeText(activity,"Kullanıcı Adı Kaydı Başarısız!",Toast.LENGTH_SHORT).show()
                }
    }*/

}