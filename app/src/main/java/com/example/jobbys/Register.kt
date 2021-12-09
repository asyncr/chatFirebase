package com.example.jobbys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.jobbys.databinding.ActivityMainBinding
import com.example.jobbys.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class Register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var dbref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = Firebase.auth
        obtenerDatos()
        this.supportActionBar?.hide()
    }

    private fun obtenerDatos(){
        binding.btnRegistrar.setOnClickListener {
            val nombre = binding.edRnombre.text.toString()
            val email = binding.edRemail.text.toString()
            val password = binding.edRcontrasena.text.toString()
            val confirmar = binding.edRconfirmar.text.toString()
            validarDatos(nombre, email, password, confirmar)
        }

    }

    private fun validarDatos(nombre:String,email:String,password: String,confirmar:String){
        if(nombre.isEmpty() || email.isEmpty() || password.isEmpty() || confirmar.isEmpty()) {
            Toast.makeText(baseContext, "Ingrese todos los datos",
                Toast.LENGTH_SHORT).show()
        }else if (password != confirmar) {
            Toast.makeText(baseContext, "Las contraseñas no coinciden.",
                Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"Datos a ingresar $email $password $confirmar",Toast.LENGTH_SHORT).show()
            //crearCuenta(email, password)
            crearCuenta(nombre,email, password)
        }
    }

    //private fun crearCuenta(email:String , password:String){
    private fun crearCuenta(nombre:String, email:String, password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this,"Registro Correcto :3",Toast.LENGTH_SHORT).show()
                    finish()
                    startActivity(Intent(this,registerSuccessfully::class.java))
                    //Agregar usuario a la Base de datos
                    agregarUsuarioBD(nombre,email,auth.currentUser?.uid!!)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    Toast.makeText(baseContext, "Existe una cuenta con ese correo",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun agregarUsuarioBD(nombre: String, email: String, uid: String) {
        Toast.makeText(this,"Creacion de la base de datos",Toast.LENGTH_SHORT).show()
        dbref = FirebaseDatabase.getInstance().getReference()
        dbref.child("User").child(uid).setValue(User(nombre,email,uid))

    }

}