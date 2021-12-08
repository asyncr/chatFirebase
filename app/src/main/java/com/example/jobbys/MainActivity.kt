package com.example.jobbys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.jobbys.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        this.supportActionBar?.hide()
        auth = Firebase.auth
        validarDatos()
        crearCuenta()
    }
    private fun crearCuenta(){
        binding.btnCrear.setOnClickListener {
            startActivity(Intent(this@MainActivity,Register::class.java))
        }
    }

    private fun validarDatos(){
        binding.btnLogin.setOnClickListener {
            val email =  binding.edLemail.text.toString()
            val password =  binding.edLpassword.text.toString()
            when {
                email.isEmpty() || password.isEmpty() -> {
                    Toast.makeText(baseContext, "Ingresa todos los campos",
                        Toast.LENGTH_SHORT).show()
                } else -> {
                iniciarSesion(email, password)
                }
            }
        }
    }


    private fun iniciarSesion(email:String,password:String){
        Toast.makeText(this,"$email $password", Toast.LENGTH_SHORT).show()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    reload()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    private fun reload(){
        val intent = Intent(this, Signup::class.java)
        finish()
        startActivity(intent)
    }
}