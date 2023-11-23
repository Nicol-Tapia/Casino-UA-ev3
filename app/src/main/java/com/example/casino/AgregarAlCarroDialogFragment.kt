package com.example.casino

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import com.example.casino.Alumnos.CarroCompras

class AgregarAlCarroDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar la vista
        val view = inflater.inflate(R.layout.agregar_al_carro, container, false)

        // Recuperar el producto de los argumentos
        val productoActual = arguments?.getParcelable<Productos>("producto")

        // Recuperar los TextViews del diseño
        val nombreProductoTextView = view.findViewById<TextView>(R.id.nombreProducto)
        val descripcionProductoTextView = view.findViewById<TextView>(R.id.descripcionProducto)
        val precioProductoTextView = view.findViewById<TextView>(R.id.precioProducto)

        // Configurar los valores de los TextViews con los atributos del producto
        nombreProductoTextView.text = productoActual?.nombre
        descripcionProductoTextView.text = productoActual?.descripcion
        precioProductoTextView.text = "Precio: $${productoActual?.precio}"

        // Recuperar el botón "Agregar al Carro" del diseño
        val btnAgregarAlCarro = view.findViewById<AppCompatButton>(R.id.btnAgregarAlCarro)

        // Agregar un listener al botón para manejar el clic
        btnAgregarAlCarro.setOnClickListener {
            // Verificar que el producto no sea nulo antes de agregarlo al carrito
            productoActual?.let {
                CarroCompras.agregarProductoAlCarro(it)
                dismiss()
            }
        }

        return view
    }
}

