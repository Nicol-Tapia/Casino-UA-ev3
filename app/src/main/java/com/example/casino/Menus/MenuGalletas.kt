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

class MenuGalletas : AppCompatActivity() {

    private lateinit var menucasagalletas: AppCompatImageButton
    private lateinit var carro: AppCompatImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_galletas)

        val menuItems1 = mutableListOf<MenuItem>()
        menuItems1.add(MenuItem("Triton", 900))

        val menuItems2 = mutableListOf<MenuItem>()
        menuItems2.add(MenuItem("ChocoChip", 700))

        val menuItems3 = mutableListOf<MenuItem>()
        menuItems3.add(MenuItem("Selz", 300))

        val menuItems4 = mutableListOf<MenuItem>()
        menuItems4.add(MenuItem("Din Don", 600))

        val menuItems5 = mutableListOf<MenuItem>()
        menuItems5.add(MenuItem("Limón", 600))

        val menuItems6 = mutableListOf<MenuItem>()
        menuItems6.add(MenuItem("Obsesión", 1000))

        val menuItems7 = mutableListOf<MenuItem>()
        menuItems7.add(MenuItem("Morocha", 500))

        val menuItems8 = mutableListOf<MenuItem>()
        menuItems8.add(MenuItem("Frac", 800))

        val menuItems9 = mutableListOf<MenuItem>()
        menuItems9.add(MenuItem("Gretel", 1000))

        // Unir las listas en una sola
        val menuItems = mutableListOf<MenuItem>()
        menuItems.addAll(menuItems1)
        menuItems.addAll(menuItems2)
        menuItems.addAll(menuItems3)
        menuItems.addAll(menuItems4)
        menuItems.addAll(menuItems5)
        menuItems.addAll(menuItems6)
        menuItems.addAll(menuItems7)
        menuItems.addAll(menuItems8)
        menuItems.addAll(menuItems9)

        val adapter = AlmuerzosAdapter(this, menuItems)
        val listView = findViewById<ListView>(R.id.lvGalletas)
        listView.adapter = adapter
        Log.d("MenuGalletas", "Número de elementos en la lista: ${menuItems.size}")
    }
}