package com.example.casino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Conf : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conf)
        auth = Firebase.auth

        val textViewNombreUsuario = findViewById<TextView>(R.id.textViewNombreUsuario)
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

        val btncambiarcontraseña = findViewById<Button>(R.id.btncambiarcontraseña)
        btncambiarcontraseña.setOnClickListener {
            val intent = Intent(this, CambioContrasenia::class.java)
            startActivity(intent)
        }

        val btncerrarsesion = findViewById<Button>(R.id.btncerrarsesion)
        btncerrarsesion.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()


        }

        val btneliminarcuenta = findViewById<Button>(R.id.btneliminarcuenta)
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

                // Authenticate the user with the provided email and password
                val user = Firebase.auth.currentUser
                val credential = EmailAuthProvider.getCredential(email, password)

                user?.reauthenticate(credential)
                    ?.addOnCompleteListener { reauthTask ->
                        if (reauthTask.isSuccessful) {
                            // Reauthentication successful, delete the account
                            user.delete()
                                .addOnCompleteListener { deleteTask ->
                                    if (deleteTask.isSuccessful) {
                                        // Account deleted successfully
                                        Toast.makeText(this, "Cuenta borrada exitosamente", Toast.LENGTH_SHORT).show()

                                        // Redirect to the main activity or perform any other necessary action
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        // Error deleting the account
                                        Toast.makeText(this, "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            // Error in reauthentication
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