package com.example.casino.BarraNav

import android.os.Build
import android.widget.ArrayAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.content.ContextCompat
import com.example.casino.Productos
import com.example.casino.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        databaseReference = FirebaseDatabase.getInstance().reference.child("productos")

        val agregarProductoButton = view.findViewById<AppCompatButton>(R.id.btnagregarproducto)
        agregarProductoButton.setOnClickListener {
            mostrarDialogoAgregarProducto()
        }

        return view

    }

    private fun mostrarDialogoAgregarProducto() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.agregar_producto, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)

        val editTextNombre = dialogView.findViewById<AppCompatEditText>(R.id.editTextNombre)
        val editTextDescripcion = dialogView.findViewById<AppCompatEditText>(R.id.editTextDescripcion)
        val editTextPrecio = dialogView.findViewById<AppCompatEditText>(R.id.editTextPrecio)
        val editTextCantidad = dialogView.findViewById<AppCompatEditText>(R.id.editTextCantidad)
        val spinnerCategoria = dialogView.findViewById<AppCompatSpinner>(R.id.spinnerCategoria)
        val btnGuardar = dialogView.findViewById<AppCompatButton>(R.id.btnguardar)

        val categorias = arrayOf("Bebidas", "Postres", "Almuerzos", "Frutas", "Comida Rápida", "Galletas", "Sandwich", "Té/Café")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categorias)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoria.adapter = adapter

        val dialog = builder.create()



        dialog.show()

        // Configura un clic en el botón Guardar
        btnGuardar.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val descripcion = editTextDescripcion.text.toString()
            val precio = editTextPrecio.text.toString().toDouble()
            val cantidad = editTextCantidad.text.toString().toInt()
            val categoria = spinnerCategoria.selectedItem.toString()

            // Crea un objeto Producto con los datos ingresados
            val producto = Productos(nombre, descripcion, precio, cantidad, categoria)

            // Agrega el producto a la base de datos de Firebase
            val productoKey = databaseReference.push().key
            productoKey?.let {
                databaseReference.child(it).setValue(producto)
            }

            // Cierra el cuadro de diálogo
            dialog.dismiss()

            // Puedes mostrar un mensaje de éxito aquí
            Toast.makeText(requireContext(), "Producto agregado con éxito", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}

