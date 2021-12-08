package com.example.jobbys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jobbys.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Signup : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Initialize Firebase Auth
        this.supportActionBar?.hide()
        auth = Firebase.auth

        cerrarSesion()
        abrirChats()
    }

    private fun cerrarSesion(){
        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this@Signup,MainActivity::class.java))
        }
    }


    private fun abrirChats() {
        binding.btnConversations.setOnClickListener {
            startActivity(Intent(this@Signup,Converse::class.java))
        }
    }

}