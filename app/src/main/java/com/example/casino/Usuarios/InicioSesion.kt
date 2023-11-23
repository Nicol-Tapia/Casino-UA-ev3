package com.example.casino.Usuarios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.example.casino.Admin.MenuAdmin
import com.example.casino.Alumnos.MenuEstudiantes
import com.example.casino.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class InicioSesion : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)

        auth = Firebase.auth

        // Botones
        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)
        // Usuario txtEdit
        val txtuser = findViewById<AppCompatEditText>(R.id.txtuser)
        // Contraseña
        val txtpassword = findViewById<AppCompatEditText>(R.id.txtpassword)

        btnIniciarSesion.setOnClickListener {
            signIn(txtuser.text.toString(), txtpassword.text.toString())
        }

        // Botón para ir a la vista registro
        val registrarUsuario = findViewById<TextView>(R.id.registerTextView)
        registrarUsuario.setOnClickListener {
            val intent = Intent(this, RegistroUsuario::class.java)
            startActivity(intent)
        }

        // Botón para ir a la vista recuperar contraseña
        val forgotPasswordTextView = findViewById<TextView>(R.id.forgotPasswordTextView)
        forgotPasswordTextView.setOnClickListener {
            val intent = Intent(this, RecuperarContrasenia::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (email == "admin@gmail.com") {
                        // Usuario es administrador
                        val intent = Intent(this, MenuAdmin::class.java)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, MenuEstudiantes::class.java)
                        startActivity(intent)
                    }
                    finish()

                } else {
                    val errorMessage = task.exception?.message ?: "Error desconocido"
                    Log.d("InicioSesion", "Error de autenticación: $errorMessage")
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }

            }
    }
}
