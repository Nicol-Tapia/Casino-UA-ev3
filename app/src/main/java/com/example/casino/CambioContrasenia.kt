package com.example.casino

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CambioContrasenia : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cambio_contrasenia)
        auth = Firebase.auth

        val contraseñaactual = findViewById<TextInputEditText>(R.id.contraseñaactual)
        val nuevacontraseña = findViewById<TextInputEditText>(R.id.nuevacontraseña)
        val confirmarcontraseña = findViewById<TextInputEditText>(R.id.confirmarcontraseña)
        val btnCambioContraseña = findViewById<Button>(R.id.btnCambioContraseña)

        btnCambioContraseña.setOnClickListener {
            val contraseñaActualStr = contraseñaactual.text.toString()
            val nuevaContraseñaStr = nuevacontraseña.text.toString()
            val confirmarcontraseña = confirmarcontraseña.text.toString()

            // Verificar si las contraseñas coinciden
            if (nuevaContraseñaStr == confirmarcontraseña) {
                val user = auth.currentUser

                if (user != null) {

                    // Verificar si la contraseña actual es correcta
                    val credential = EmailAuthProvider.getCredential(user.email!!, contraseñaActualStr)
                    user.reauthenticate(credential)
                        .addOnCompleteListener { reauthTask ->

                            if (reauthTask.isSuccessful) {
                                // Cambiar la contraseña
                                user.updatePassword(nuevaContraseñaStr)
                                    .addOnCompleteListener { updateTask ->

                                        if (updateTask.isSuccessful) {
                                            // La contraseña se cambió con éxito
                                            Toast.makeText(this, "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show()
                                            finish() // Cierra la actividad después de cambiar la contraseña
                                        } else {
                                            // Manejar errores al cambiar la contraseña
                                            Toast.makeText(this, "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            } else {
                                // Manejar errores al autenticar de nuevo al usuario
                                Toast.makeText(this, "Error de autenticación", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            } else {
                // Las contraseñas nuevas no coinciden
                Toast.makeText(this, "Las contraseñas nuevas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }


    }
}