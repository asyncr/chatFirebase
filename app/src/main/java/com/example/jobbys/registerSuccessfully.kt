package com.example.jobbys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jobbys.databinding.ActivityRegisterSuccessfullyBinding

class registerSuccessfully : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterSuccessfullyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterSuccessfullyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.supportActionBar?.hide()
        continuar()
    }

    private fun continuar(){
        binding.btnContinue.setOnClickListener {
            finish()
            startActivity(Intent(this,Signup::class.java))
        }
    }
}