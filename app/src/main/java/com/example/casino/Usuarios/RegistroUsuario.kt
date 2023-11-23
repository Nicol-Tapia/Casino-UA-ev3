package com.example.casino.Usuarios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.casino.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.FirebaseDatabase

class RegistroUsuario : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario)

        auth = Firebase.auth

        // Botón de registro
        val btnRegistro = findViewById<Button>(R.id.btnRegitro)

        // Obtener campos para el registro
        val txtUsuario = findViewById<TextInputEditText>(R.id.usernameEditText)
        val txtPassword = findViewById<TextInputEditText>(R.id.passwordEditText)
        val txtConfirmPassword = findViewById<TextInputEditText>(R.id.confirmPasswordEditText)

        // Registrar usuario
        btnRegistro.setOnClickListener {
            if (txtPassword.text.toString() == txtConfirmPassword.text.toString()) {
                // Llama a la función para crear el usuario con rol
                crearUsuarioConRol(txtUsuario.text.toString(), txtPassword.text.toString())
            } else {
                // Contraseñas no coinciden
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun crearUsuarioConRol(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userId = user?.uid

                    if (userId != null) {
                        // Crea un HashMap con el campo "rol" y otros datos necesarios
                        val userData = HashMap<String, Any>()
                        userData["rol"] = "normal" // Establece el rol como "normal" por defecto

                        // Obtiene una referencia a la base de datos
                        val databaseReference = FirebaseDatabase.getInstance().reference
                        // Guarda los datos del usuario en la base de datos
                        databaseReference.child("usuarios").child(userId).setValue(userData)

                        Toast.makeText(this, "Se creó correctamente el usuario", Toast.LENGTH_SHORT).show()

                        // Ir a la vista iniciar sesión
                        val intent = Intent(this, InicioSesion::class.java)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
