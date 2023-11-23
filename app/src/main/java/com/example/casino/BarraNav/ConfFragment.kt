package com.example.casino.BarraNav

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.example.casino.Usuarios.CambioContrasenia
import com.example.casino.MainActivity
import com.example.casino.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ConfFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_conf, container, false)

        val textViewNombreUsuario = rootView.findViewById<TextView>(R.id.textViewNombreUsuario)
        val btncambiarcontraseña = rootView.findViewById<AppCompatButton>(R.id.btncambiarcontraseña)
        val btncerrarsesion = rootView.findViewById<AppCompatButton>(R.id.btncerrarsesion)
        val btneliminarcuenta = rootView.findViewById<AppCompatButton>(R.id.btneliminarcuenta)

        val user = Firebase.auth.currentUser
        if (user != null) {
            val correoUsuario = user.email
            if (correoUsuario != null) {
                textViewNombreUsuario.text = "Bienvenido $correoUsuario"
            } else {
                Toast.makeText(requireContext(), "Error, correo de usuario nulo", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Error, no hay usuario autenticado", Toast.LENGTH_SHORT).show()
        }

        btncambiarcontraseña.setOnClickListener {
            val intent = Intent(requireContext(), CambioContrasenia::class.java)
            startActivity(intent)
        }

        btncerrarsesion.setOnClickListener {
            Firebase.auth.signOut()

            // Verificar si la actividad está en un estado en el que se pueda finalizar
            if (!requireActivity().isFinishing) {
                // Esperar a que la actividad actual sea finalizada antes de iniciar la nueva actividad
                requireActivity().finish()

                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        }



        btneliminarcuenta.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
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
                                        Toast.makeText(requireContext(), "Cuenta borrada exitosamente", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(requireContext(), MainActivity::class.java)
                                        startActivity(intent)
                                        requireActivity().finish()
                                    } else {
                                        Toast.makeText(requireContext(), "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            Toast.makeText(requireContext(), "Error en credenciales, email o contraseña incorrecta", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConfFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
