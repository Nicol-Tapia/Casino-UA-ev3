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

class MenuFrutas : AppCompatActivity() {
    private lateinit var menucasafrutas: AppCompatImageButton
    private lateinit var carro: AppCompatImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_frutas)

        val menuItems1 = mutableListOf<MenuItemBebidas>()
        menuItems1.add(MenuItemBebidas("Manzanas", 350))

        val menuItems2 = mutableListOf<MenuItemBebidas>()
        menuItems2.add(MenuItemBebidas("Plátanos", 350))

        val menuItems3 = mutableListOf<MenuItemBebidas>()
        menuItems3.add(MenuItemBebidas("Naranjas", 350))

        // Unir las listas en una sola
        val menuItems = mutableListOf<MenuItemBebidas>()
        menuItems.addAll(menuItems1)
        menuItems.addAll(menuItems2)
        menuItems.addAll(menuItems3)


        val adapter = BebidasAdapter(this, menuItems)
        val listView = findViewById<ListView>(R.id.lvFrutas)
        listView.adapter = adapter
        Log.d("MenuBebidas", "Número de elementos en la lista: ${menuItems.size}")
    }
}