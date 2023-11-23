package com.example.casino.Alumnos.PagMenus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.casino.AgregarAlCarroDialogFragment
import com.example.casino.Alumnos.CarroCompras
import com.example.casino.Alumnos.MenuEstudiantes
import com.example.casino.Productos
import com.example.casino.ProductosAdapterAlumnos
import com.example.casino.R
import com.google.firebase.database.*

class MenuAlmuerzos : AppCompatActivity() {

    private lateinit var btnhome: AppCompatImageButton
    private lateinit var carro: AppCompatImageButton
    private lateinit var database: DatabaseReference
    private lateinit var adapter: ProductosAdapterAlumnos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_almuerzos)

        database = FirebaseDatabase.getInstance().reference

        btnhome = findViewById(R.id.btnhome)
        btnhome.setOnClickListener {
            val intent = Intent(this, MenuEstudiantes::class.java)
            startActivity(intent)
        }

        val fatras = findViewById<AppCompatImageButton>(R.id.fatras)
        fatras.setOnClickListener {
            onBackPressed()
        }

        carro = findViewById(R.id.carro)
        carro.setOnClickListener {
            val intent = Intent(this, CarroCompras::class.java)
            startActivity(intent)
        }

        // Crear una referencia a la base de datos en la ubicación que contiene los productos
        val productosRef = database.child("productos")

        adapter = ProductosAdapterAlumnos(
            listOf(),
            { producto ->
                // Handle item click - Muestra el diálogo o realiza la lógica necesaria aquí
                val fragmentManager = supportFragmentManager
                val dialogFragment = AgregarAlCarroDialogFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable("producto", producto)
                    }
                }

                // Después de agregar el producto al carrito, restar la cantidad del stock
                restarCantidadDelStock(producto)

                dialogFragment.show(fragmentManager, "AgregarAlCarroDialogFragment")
            },
            { producto -> /* Handle long click if needed */ }
        )

        // Configurar el RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Leer los datos desde Firebase
        productosRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Limpiar la lista actual
                val menuItems = mutableListOf<Productos>()

                // Iterar sobre los datos de la base de datos
                for (productoSnapshot in snapshot.children) {
                    val producto = productoSnapshot.getValue(Productos::class.java)

                    // Agrega un log para imprimir información sobre los productos recuperados
                    Log.d("MenuAlmuerzo", "Producto recuperado de la base de datos: $producto")

                    // Verificar si el producto pertenece a la categoría "almuerzos"
                    if (producto?.categoria == "Almuerzos") {
                        // Agregar el producto a la lista
                        menuItems.add(producto)
                    }
                }

                // Actualizar el adaptador con la nueva lista de productos
                adapter.setProductos(menuItems)
                Log.d("MenuAlmuerzo", "Número de elementos en la lista: ${menuItems.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error de lectura de la base de datos
                Log.e("MenuAlmuerzo", "Error al leer datos desde Firebase", error.toException())
            }
        })
    }

    private fun restarCantidadDelStock(producto: Productos) {
        val productoRef = database.child("productos").child(producto.id)

        // Restar la cantidad del producto en el stock
        val nuevaCantidad = producto.cantidad - 1
        productoRef.child("cantidad").setValue(nuevaCantidad)

        // Notificar al administrador o actualizar la lista del administrador
        // Puedes implementar la lógica específica aquí según tus necesidades
    }
}
