package com.example.casino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistroUsuario : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario)

        auth = Firebase.auth

        //btn registrar
        val btnRegitro = findViewById<Button>(R.id.btnRegitro)

        //obtener campos para el registro
        val txtUsuario = findViewById<TextInputEditText>(R.id.usernameEditText)
        val txtPassword = findViewById<TextInputEditText>(R.id.passwordEditText)
        val txtConfirmPassword = findViewById<TextInputEditText>(R.id.confirmPasswordEditText)

        //Registrar usuario
        btnRegitro.setOnClickListener {
            if (txtPassword.text.toString() == txtConfirmPassword.text.toString()) {
                crearUsuario(txtUsuario.text.toString(), txtPassword.text.toString())
            } else {
                //contraseñas no coinciden
                Toast.makeText(this, "Las contraseñas no coiciden", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun crearUsuario(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Se creo correctamente el usuario", Toast.LENGTH_SHORT)
                        .show()
                    //Ir a la vista iniciar sesión
                    val intent = Intent(this, InicioSesion::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show()
                }
            }
    }
}