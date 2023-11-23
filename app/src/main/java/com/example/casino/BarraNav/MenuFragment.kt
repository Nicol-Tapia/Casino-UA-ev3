package com.example.casino.BarraNav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.casino.Productos
import com.example.casino.ProductosAdapter
import com.example.casino.R
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.UUID
import android.text.Editable

class MenuFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var productosAdapter: ProductosAdapter
    private val productosList: MutableList<Productos> = mutableListOf()
    private val categorias = arrayOf(
        "Bebidas",
        "Postres",
        "Almuerzos",
        "Frutas",
        "Comida Rápida",
        "Galletas",
        "Sandwich",
        "Té/Café"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        databaseReference = FirebaseDatabase.getInstance().reference.child("productos")
        FirebaseApp.initializeApp(requireContext())

        recyclerView = view.findViewById(R.id.recyclerView)
        productosAdapter = ProductosAdapter(productosList) { productKey ->
            showProductDialog(productKey)
        }
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
                        // Verifica si la cantidad es mayor que 0 antes de agregarlo a la lista
                        if (it.cantidad > 0) {
                            productosList.add(it)
                        } else {
                            // Si la cantidad es 0, elimina el producto de la base de datos
                            dataSnapshot.ref.removeValue()
                        }
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
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.agregar_producto, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)

        val editTextNombre = dialogView.findViewById<AppCompatEditText>(R.id.editTextNombre)
        val editTextDescripcion = dialogView.findViewById<AppCompatEditText>(R.id.editTextDescripcion)
        val editTextPrecio = dialogView.findViewById<AppCompatEditText>(R.id.editTextPrecio)
        val editTextCantidad = dialogView.findViewById<AppCompatEditText>(R.id.editTextCantidad)
        val spinnerCategoria = dialogView.findViewById<AppCompatSpinner>(R.id.spinnerCategoria)
        val btnGuardar = dialogView.findViewById<AppCompatButton>(R.id.btnguardar)

        // Agrega el texto de selección al principio de la lista
        val categoriasConHint = listOf(getString(R.string.hint_categoria)) + categorias
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoriasConHint)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoria.adapter = adapter

        val dialog = builder.create()

        dialog.show()

        // Configura un clic en el botón Guardar
        btnGuardar.setOnClickListener {
            // Validar que todos los campos estén llenos
            if (validateFields(editTextNombre, editTextDescripcion, editTextPrecio, editTextCantidad, spinnerCategoria)) {
                val id = UUID.randomUUID().toString()
                val nombre = editTextNombre.text.toString()
                val descripcion = editTextDescripcion.text.toString()
                val precio = editTextPrecio.text.toString().toDouble()
                val cantidad = editTextCantidad.text.toString().toInt()
                val categoria = spinnerCategoria.selectedItem.toString()

                val producto = Productos(id, nombre, descripcion, precio, cantidad, categoria)

                databaseReference.child(id).setValue(producto)


                // Cierra el cuadro de diálogo
                dialog.dismiss()


                // Puedes mostrar un mensaje de éxito aquí
                Toast.makeText(requireContext(), "Producto agregado con éxito", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    // Método para validar que todos los campos estén llenos
    private fun validateFields(
        nombre: AppCompatEditText,
        descripcion: AppCompatEditText,
        precio: AppCompatEditText,
        cantidad: AppCompatEditText,
        categoria: AppCompatSpinner
    ): Boolean {
        if (
            nombre.text.isNullOrBlank() ||
            descripcion.text.isNullOrBlank() ||
            precio.text.isNullOrBlank() ||
            cantidad.text.isNullOrBlank() ||
            categoria.selectedItem == null
        ) {
            // Mostrar un mensaje de error si algún campo está vacío
            Toast.makeText(
                nombre.context,
                "Por favor, complete todos los campos",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }

    private fun showProductDialog(productKey: String) {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.editar_eliminar_productos, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)

        val editTextNombre = dialogView.findViewById<AppCompatEditText>(R.id.editTextNombre)
        val editTextDescripcion =
            dialogView.findViewById<AppCompatEditText>(R.id.editTextDescripcion)
        val editTextPrecio = dialogView.findViewById<AppCompatEditText>(R.id.editTextPrecio)
        val editTextCantidad = dialogView.findViewById<AppCompatEditText>(R.id.editTextCantidad)
        val spinnerCategoria = dialogView.findViewById<AppCompatSpinner>(R.id.spinnerCategoria)
        val btnGuardarEdicion = dialogView.findViewById<AppCompatButton>(R.id.btnguardar_edicion)
        val btnEliminar = dialogView.findViewById<AppCompatButton>(R.id.btn_eliminar)

        val producto = productosList.find { it.id == productKey }

        producto?.let {
            editTextNombre.text = it.nombre.toEditable()
            editTextDescripcion.text = it.descripcion.toEditable()
            editTextPrecio.text = it.precio.toString().toEditable()
            editTextCantidad.text = it.cantidad.toString().toEditable()

            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categorias)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategoria.adapter = adapter
            spinnerCategoria.setSelection(categorias.indexOf(it.categoria))

            val dialog = builder.create()
            dialog.show()

            btnGuardarEdicion.setOnClickListener {
                try {
                    val editedNombre = editTextNombre.text.toString()
                    val editedDescripcion = editTextDescripcion.text.toString()
                    val editedPrecio = editTextPrecio.text.toString().toDouble()
                    val editedCantidad = editTextCantidad.text.toString().toInt()
                    val editedCategoria = spinnerCategoria.selectedItem.toString()

                    val editedProduct = Productos(
                        productKey,
                        editedNombre,
                        editedDescripcion,
                        editedPrecio,
                        editedCantidad,
                        editedCategoria
                    )

                    // Actualiza el producto en la base de datos con la clave existente (productKey)
                    databaseReference.child(productKey).setValue(editedProduct)

                    val index = productosList.indexOfFirst { it.id == productKey }
                    if (index != -1) {
                        productosList[index] = editedProduct
                        productosAdapter.notifyDataSetChanged()
                    }

                    dialog.dismiss()
                    Toast.makeText(
                        requireContext(),
                        "Edición guardada con éxito",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        requireContext(),
                        "Error al guardar la edición",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            btnEliminar.setOnClickListener {
                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog.setTitle("Eliminar Producto")
                alertDialog.setMessage("¿Está seguro de que desea eliminar este producto?")

                alertDialog.setPositiveButton("Sí") { _, _ ->
                    // Eliminar el producto de la base de datos
                    eliminarProducto(productKey)
                    dialog.dismiss()
                }

                alertDialog.setNegativeButton("No") { _, _ ->
                    // No hacer nada, simplemente cerrar el diálogo
                    dialog.dismiss()
                }

                alertDialog.show()
            }
        }
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun eliminarProducto(productKey: String) {
        // Elimina el producto de la base de datos
        databaseReference.child(productKey).removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Producto eliminado con éxito de la base de datos
                Toast.makeText(requireContext(), "Producto eliminado con éxito", Toast.LENGTH_SHORT).show()

                // Actualiza la lista y el RecyclerView
                cargarDatosDesdeFirebase()
            } else {
                Toast.makeText(requireContext(), "Error al eliminar el producto", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
