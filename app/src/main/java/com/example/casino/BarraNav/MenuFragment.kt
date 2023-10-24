package com.example.casino.BarraNav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSpinner
import com.example.casino.Productos
import com.example.casino.ProductosAdapter
import com.example.casino.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MenuFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var productosAdapter: ProductosAdapter
    private val productosList: MutableList<Productos> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        databaseReference = FirebaseDatabase.getInstance().reference.child("productos")

        recyclerView = view.findViewById(R.id.recyclerView)
        productosAdapter = ProductosAdapter(productosList)
        recyclerView.adapter = productosAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val agregarProductoButton = view.findViewById<AppCompatButton>(R.id.btnagregarproducto)
        agregarProductoButton.setOnClickListener {
            mostrarDialogoAgregarProducto()
        }

        cargarDatosDesdeFirebase()

        return view
    }

    private fun cargarDatosDesdeFirebase() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productosList.clear()
                for (dataSnapshot in snapshot.children) {
                    val producto = dataSnapshot.getValue(Productos::class.java)
                    producto?.let {
                        productosList.add(it)
                    }
                }
                productosAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar error
                Toast.makeText(requireContext(), "Error al cargar datos", Toast.LENGTH_SHORT).show()
            }
        })
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

}

