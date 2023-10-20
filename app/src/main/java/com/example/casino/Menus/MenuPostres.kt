package com.example.casino.Menus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.widget.AppCompatImageButton
import com.example.casino.Bebidas.BebidasAdapter
import com.example.casino.Bebidas.MenuItemBebidas
import com.example.casino.CarroCompras
import com.example.casino.InicioSesionFijo
import com.example.casino.MainActivity
import com.example.casino.Menu
import com.example.casino.R

class MenuPostres : AppCompatActivity() {

    private lateinit var btncasapostres: AppCompatImageButton
    private lateinit var carro: AppCompatImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_postres)


        val menuItems1 = mutableListOf<MenuItemBebidas>()
        menuItems1.add(MenuItemBebidas("Donas", 800))

        val menuItems2 = mutableListOf<MenuItemBebidas>()
        menuItems2.add(MenuItemBebidas("Cupcake", 700))

        val menuItems3 = mutableListOf<MenuItemBebidas>()
        menuItems3.add(MenuItemBebidas("Tortas", 1200))

        // Unir las listas en una sola
        val menuItems = mutableListOf<MenuItemBebidas>()
        menuItems.addAll(menuItems1)
        menuItems.addAll(menuItems2)
        menuItems.addAll(menuItems3)
        val adapter = BebidasAdapter(this, menuItems)
        val listView = findViewById<ListView>(R.id.lvPostres)
        listView.adapter = adapter
        Log.d("MenuPostres", "Número de elementos en la lista: ${menuItems.size}")
    }

}