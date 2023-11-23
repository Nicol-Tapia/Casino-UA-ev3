package com.example.casino.Alumnos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.casino.Pagos
import com.example.casino.Productos
import com.example.casino.ProductosAdapterAlumnos
import com.example.casino.R

class CarroCompras : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductosAdapterAlumnos
    private lateinit var totalPriceTextView: TextView
    private lateinit var menucasa: AppCompatImageButton
    private lateinit var pagar: AppCompatImageButton
    private lateinit var eliminarButton: Button

    companion object {
        private lateinit var listaProductos: MutableList<Productos>
        private val selectedItems = HashSet<Productos>()

        fun agregarProductoAlCarro(producto: Productos) {
            if (!::listaProductos.isInitialized) {
                listaProductos = mutableListOf()
            }
            listaProductos.add(producto)
        }

        fun obtenerListaProductos(): List<Productos> {
            return if (::listaProductos.isInitialized) listaProductos else emptyList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro_compras)

        menucasa = findViewById(R.id.menucasa)
        menucasa.setOnClickListener {
            val intent = Intent(this, MenuEstudiantes::class.java)
            startActivity(intent)
        }
        pagar = findViewById(R.id.pagar)
        pagar.setOnClickListener {
            val intent = Intent(this, Pagos::class.java)
            intent.putExtra("total", obtenerTotalCarrito())
            intent.putParcelableArrayListExtra("productos", ArrayList(obtenerListaProductos()))
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.recyclerViewCarro)
        adapter = ProductosAdapterAlumnos(
            obtenerListaProductos(),
            { producto -> toggleItemSelection(producto) },
            { producto -> eliminarProductos(producto) }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        totalPriceTextView = findViewById(R.id.totalPriceTextView)

        // Calculate and display the initial total price
        updateTotalPrice()
    }

    private fun toggleItemSelection(producto: Productos) {
        producto.toggleSelection()

        if (producto.isSelected) {
            selectedItems.add(producto)
        } else {
            selectedItems.remove(producto)
        }

        adapter.notifyDataSetChanged()
        updateTotalPrice()
    }

    private fun eliminarProductosSeleccionados() {
        val productosSeleccionados = selectedItems.toList()

        for (producto in productosSeleccionados) {
            CarroCompras.listaProductos.remove(producto)
        }

        adapter.notifyDataSetChanged()
        updateTotalPrice()
    }

    private fun eliminarProductos(producto: Productos) {
        CarroCompras.listaProductos.remove(producto)
        adapter.notifyDataSetChanged()
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        val total = obtenerListaProductos().sumByDouble { it.precio.toDouble() }
        totalPriceTextView.text = getString(R.string.total_price_placeholder, total)
    }

    private fun obtenerTotalCarrito(): Double {
        return obtenerListaProductos().sumByDouble { it.precio.toDouble() }
    }
}
