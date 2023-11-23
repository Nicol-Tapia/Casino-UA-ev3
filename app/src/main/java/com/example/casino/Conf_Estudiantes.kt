package com.example.casino

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.casino.Usuarios.CambioContrasenia
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Conf_Estudiantes : AppCompatActivity() {

    // Declarar las variables
    private lateinit var textViewNombreUsuario: TextView
    private lateinit var btncambiarcontraseña: AppCompatButton
    private lateinit var btncerrarsesion: AppCompatButton
    private lateinit var btneliminarcuenta: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conf_estudiantes)

        // Inicializar las variables
        textViewNombreUsuario = findViewById(R.id.textViewNombreUsuario)
        btncambiarcontraseña = findViewById(R.id.btncambiarcontraseña)
        btncerrarsesion = findViewById(R.id.btncerrarsesion)
        btneliminarcuenta = findViewById(R.id.btneliminarcuenta)

        val user = Firebase.auth.currentUser
        if (user != null) {
            val correoUsuario = user.email
            if (correoUsuario != null) {
                textViewNombreUsuario.text = "Bienvenido $correoUsuario"
            } else {
                Toast.makeText(this, "Error, correo de usuario nulo", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Error, no hay usuario autenticado", Toast.LENGTH_SHORT).show()
        }

        btncambiarcontraseña.setOnClickListener {
            val intent = Intent(this, CambioContrasenia::class.java)
            startActivity(intent)
        }

        btncerrarsesion.setOnClickListener {
            Firebase.auth.signOut()

            // Esperar a que la actividad actual sea finalizada antes de iniciar la nueva actividad
            finish()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btneliminarcuenta.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_delete_account, null)
            builder.setView(dialogView)
            builder.setTitle("Confirmar la eliminación de la cuenta")

            val inputEmail = dialogView.findViewById<EditText>(R.id.inputEmail)
            val inputPassword = dialogView.findViewById<EditText>(R.id.inputPassword)

            builder.setPositiveButton("Cuenta Eliminada") { _, _ ->
                val email = inputEmail.text.toString()
                val password = inputPassword.text.toString()

                val user = Firebase.auth.currentUser
                val credential = EmailAuthProvider.getCredential(email, password)

                user?.reauthenticate(credential)
                    ?.addOnCompleteListener { reauthTask ->
                        if (reauthTask.isSuccessful) {
                            user.delete()
                                .addOnCompleteListener { deleteTask ->
                                    if (deleteTask.isSuccessful) {
                                        Toast.makeText(this, "Cuenta borrada exitosamente", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(this, "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            Toast.makeText(this, "Error en credenciales, email o contraseña incorrecta", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }
    }
}
