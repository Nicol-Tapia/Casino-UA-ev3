package com.example.casino.Menus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.widget.AppCompatImageButton
import com.example.casino.Almuerzos.AlmuerzosAdapter
import com.example.casino.Almuerzos.MenuItem
import com.example.casino.CarroCompras
import com.example.casino.InicioSesionFijo
import com.example.casino.MainActivity
import com.example.casino.Menu
import com.example.casino.R

class MenuSandwich : AppCompatActivity() {

    private lateinit var menucasasandwich: AppCompatImageButton
    private lateinit var carro: AppCompatImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sandwich)

        // Crear listas de menús
        val menuItems1 = mutableListOf<MenuItem>()
        menuItems1.add(MenuItem("Pan con Queso", 800))

        val menuItems2 = mutableListOf<MenuItem>()
        menuItems2.add(MenuItem("Pan con Jamón", 700))

        val menuItems3 = mutableListOf<MenuItem>()
        menuItems3.add(MenuItem("Pan Aliado", 900))

        val menuItems4 = mutableListOf<MenuItem>()
        menuItems4.add(MenuItem("Pan con Huevo", 1000))

        val menuItems5 = mutableListOf<MenuItem>()
        menuItems5.add(MenuItem("Pan con Ave Mayo", 1200))

        val menuItems6 = mutableListOf<MenuItem>()
        menuItems6.add(MenuItem("Pan con Palta", 1400))


        // Unir las listas en una sola
        val menuItems = mutableListOf<MenuItem>()
        menuItems.addAll(menuItems1)
        menuItems.addAll(menuItems2)
        menuItems.addAll(menuItems3)
        menuItems.addAll(menuItems4)
        menuItems.addAll(menuItems5)
        menuItems.addAll(menuItems6)


        val adapter = AlmuerzosAdapter(this, menuItems)
        val listView = findViewById<ListView>(R.id.lvSandwich)
        listView.adapter = adapter
        Log.d("MenuAlmuerzo", "Número de elementos en la lista: ${menuItems.size}")
    }
}