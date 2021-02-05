package com.example.loginscreenfirebase.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.example.loginscreenfirebase.R
import com.example.loginscreenfirebase.databinding.ActivityWelcomeBinding
import com.example.loginscreenfirebase.fragments.ForgotPasswordFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class WelcomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityWelcomeBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        auth = Firebase.auth
        binding.textView.text = auth.currentUser!!.email
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_reset_password -> {
                val mail = auth.currentUser!!.email
                auth.sendPasswordResetEmail(mail!!).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(this,"Mail Sended!.", Toast.LENGTH_SHORT).show()
                        auth.signOut()
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(this,exception.localizedMessage, Toast.LENGTH_SHORT).show()
                }
                return true
            }
            R.id.action_delete_account -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Are you sure you want to your account?")
                builder.setPositiveButton("Yes"){ dialogInterface, which ->
                    val user = Firebase.auth.currentUser!!
                    user.delete().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this,"Account Deleted!",Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_SHORT).show()
                    }
                }
                builder.setNegativeButton("No"){ dialogInterface, which ->
                    dialogInterface.dismiss()
                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(true)
                alertDialog.show()
                return true
            }
            R.id.action_sign_out -> {
                auth.signOut()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
        return true
    }

}