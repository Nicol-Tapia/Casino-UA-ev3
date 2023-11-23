package com.example.casino

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.casino.Alumnos.CarroCompras
import com.example.casino.Alumnos.PagMenus.MenuAlmuerzos

class ProductosAdapterAlumnos(
    private var productos: List<Productos>,
    private val onItemClick: (Productos) -> Unit,
    private val onItemLongClick: (Productos) -> Unit
) : RecyclerView.Adapter<ProductosAdapterAlumnos.ViewHolder>() {

    private val selectedItems = HashSet<Productos>()

    // Métodos para actualizar y obtener productos seleccionados
    fun setProductos(productos: List<Productos>) {
        this.productos = productos
        notifyDataSetChanged()
    }

    fun getSelectedItems(): Set<Productos> {
        return selectedItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = productos[position]

        // Asigna los datos del producto a las vistas en el diseño del elemento
        holder.nombreProducto.text = producto.nombre
        holder.descripcion.text = producto.descripcion
        holder.precio.text = "Precio: $" + producto.precio.toString()

        // Obtén la actividad actual
        val currentActivity = holder.itemView.context

        holder.itemView.setOnClickListener {
            // Verifica si la actividad actual no es CarroCompras o Pagos
            if (currentActivity !is CarroCompras && currentActivity !is Pagos) {
                // Obtén la posición del elemento clickeado
                val position = holder.adapterPosition
                // Verifica si la posición es válida
                if (position != RecyclerView.NO_POSITION) {
                    // Obtiene el producto en la posición clickeada

                    val producto = productos[position]

                    // Crea un nuevo diálogo y pasa el producto como argumento
                    val fragmentManager = (currentActivity as AppCompatActivity).supportFragmentManager
                    val dialogFragment = AgregarAlCarroDialogFragment().apply {
                        arguments = Bundle().apply {
                            putParcelable("producto", producto)
                        }
                    }

                    dialogFragment.show(fragmentManager, "AgregarAlCarroDialogFragment")
                }
            }
        }

        holder.itemView.setOnLongClickListener {
            val producto = productos[holder.adapterPosition]
            onItemLongClick(producto)
            true
        }

        // Actualizar la apariencia según si el elemento está seleccionado o no
        holder.itemView.isActivated = producto.isSelected
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreProducto: TextView = itemView.findViewById(R.id.nombre_producto)
        val descripcion: TextView = itemView.findViewById(R.id.descripcion)
        val precio: TextView = itemView.findViewById(R.id.precio)

        init {
            // Agregar el evento de clic al elemento del adaptador
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val producto = productos[position]
                    onItemClick(producto)
                }
            }
        }
    }
}
