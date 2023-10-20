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

class MenuAlmuerzo : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_almuerzo)

        // Crear listas de menús
        val menuItems1 = mutableListOf<MenuItem>()
        menuItems1.add(MenuItem("Menú Público", 2600))

        val menuItems2 = mutableListOf<MenuItem>()
        menuItems2.add(MenuItem("Menú Junaeb", 3200))

        val menuItems3 = mutableListOf<MenuItem>()
        menuItems3.add(MenuItem("Menú Vegetariano", 2900))

        // Unir las listas en una sola
        val menuItems = mutableListOf<MenuItem>()
        menuItems.addAll(menuItems1)
        menuItems.addAll(menuItems2)
        menuItems.addAll(menuItems3)

        val adapter = AlmuerzosAdapter(this, menuItems)
        val listView = findViewById<ListView>(R.id.lvAlmuerzos)
        listView.adapter = adapter
        Log.d("MenuAlmuerzo", "Número de elementos en la lista: ${menuItems.size}")

    }
}
