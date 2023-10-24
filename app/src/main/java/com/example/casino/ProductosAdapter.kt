package com.example.casino
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.casino.Productos
import com.example.casino.R

class ProductosAdapter(
    private val productos: MutableList<Productos>
) : RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>() {

    // Listener para manejar la selección de categoría
    private var onCategoriaSelectedListener: ((Int, String) -> Unit)? = null

    // Método para establecer el listener
    fun setOnCategoriaSelectedListener(listener: (Int, String) -> Unit) {
        onCategoriaSelectedListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        holder.spinnerCategory.setSelection(getCategoriaPosition(producto.categoria))

        // Maneja la selección de categoría en el Spinner
        holder.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                onCategoriaSelectedListener?.invoke(holder.adapterPosition, parent?.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val spinnerCategory: Spinner = itemView.findViewById(R.id.categoria)
    }

    // Busca la posición de la categoría seleccionada en el Spinner
    private fun getCategoriaPosition(categoria: String): Int {
        val categorias = arrayOf("Bebidas", "Postres", "Almuerzos", "Frutas", "Comida Rápida", "Galletas", "Sandwich", "Té/Café")
        return categorias.indexOf(categoria)
    }
}
