package com.example.casino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class InicioSesion : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)

        auth = Firebase.auth

        //Botones
        val btnIniciarSesion = findViewById<Button>(R.id.btnIniciarSesion)
        //Usuario txtEdit
        val txtuser = findViewById<AppCompatEditText>(R.id.txtuser)
        //Contraseña
        val txtpassword = findViewById<AppCompatEditText>(R.id.txtpassword)
        btnIniciarSesion.setOnClickListener {
            signIn(txtuser.text.toString(), txtpassword.text.toString())
        }
        //botón para ir a la vista registro
        val registrarUsuario = findViewById<TextView>(R.id.registerTextView)
        registrarUsuario.setOnClickListener {
            val intent = Intent(this, RegistroUsuario::class.java)
            startActivity(intent)
        }
        //botón para ir a la vista recuperar contraseña
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
                    Toast.makeText(this, user?.uid.toString(), Toast.LENGTH_SHORT).show()
                    // Redirigir a la vista Inicio
                    val intent = Intent(this, Menu::class.java)
                    startActivity(intent)
                    finish() // Opcional: para cerrar la actividad actual

                } else {
                    val errorMessage = task.exception?.message ?: "Error desconocido"
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
    }

}