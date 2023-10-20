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

class MenuComidaRapida : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_menu_comida_rapida)

        val menuItems1 = mutableListOf<MenuItemBebidas>()
        menuItems1.add(MenuItemBebidas("Completo\nPalta-Mayo", 1500))

        val menuItems2 = mutableListOf<MenuItemBebidas>()
        menuItems2.add(MenuItemBebidas("Completo\nItaliano", 2000))

        val menuItems3 = mutableListOf<MenuItemBebidas>()
        menuItems3.add(MenuItemBebidas("Chaparritas", 2000))
        val menuItems4 = mutableListOf<MenuItemBebidas>()
        menuItems4.add(MenuItemBebidas("Pizza Normal", 1200))
        val menuItems5 = mutableListOf<MenuItemBebidas>()
        menuItems5.add(MenuItemBebidas("Pizza Vegetariana", 1500))

        // Unir las listas en una sola
        val menuItems = mutableListOf<MenuItemBebidas>()
        menuItems.addAll(menuItems1)
        menuItems.addAll(menuItems2)
        menuItems.addAll(menuItems3)
        menuItems.addAll(menuItems4)
        menuItems.addAll(menuItems5)

        val adapter = BebidasAdapter(this, menuItems)
        val listView = findViewById<ListView>(R.id.lvComidaRapida)
        listView.adapter = adapter
        Log.d("MenuComidaRapida", "Número de elementos en la lista: ${menuItems.size}")
    }
}