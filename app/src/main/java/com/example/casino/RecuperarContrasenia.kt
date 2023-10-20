package com.example.casino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecuperarContrasenia : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_contrasenia)
        auth = Firebase.auth

        //btn registrar
        val btnRecuperar = findViewById<Button>(R.id.btnRecuperar)

        //obtener campos para el registro
        val usernameEditText = findViewById<TextInputEditText>(R.id.usernameEditText)

        //Recuperar contraseña
        btnRecuperar.setOnClickListener {
            auth.sendPasswordResetEmail(usernameEditText.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Está Bien", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}