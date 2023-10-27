package com.example.casino

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductosAdapter(
    private val productos: MutableList<Productos>,
    private val onProductClickListener: (String) -> Unit
) : RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        // Asigna los datos del producto a las vistas en el diseño del elemento
        holder.nombreProducto.text = producto.nombre
        holder.descripcion.text = producto.descripcion
        holder.precio.text = "Precio: $" + producto.precio.toString()
        holder.cantidad.text = "Cantidad: " + producto.cantidad.toString()
        holder.categoria.text = "Categoría: " + producto.categoria

        holder.itemView.setOnClickListener {
            onProductClickListener(producto.id)
        }
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreProducto: TextView = itemView.findViewById(R.id.nombre_producto)
        val descripcion: TextView = itemView.findViewById(R.id.descripcion)
        val precio: TextView = itemView.findViewById(R.id.precio)
        val cantidad: TextView = itemView.findViewById(R.id.cantidad)
        val categoria: TextView = itemView.findViewById(R.id.categoria)
    }
}
