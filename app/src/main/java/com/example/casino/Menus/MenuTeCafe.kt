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

class MenuTeCafe : AppCompatActivity() {

    private lateinit var menucasatecafe: AppCompatImageButton
    private lateinit var carro: AppCompatImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_te_cafe)

        // Crear listas de menús
        val menuItems1 = mutableListOf<MenuItem>()
        menuItems1.add(MenuItem("Té", 900))

        val menuItems2 = mutableListOf<MenuItem>()
        menuItems2.add(MenuItem("Café Tradicional", 700))

        val menuItems3 = mutableListOf<MenuItem>()
        menuItems3.add(MenuItem("Cappuccino Chico", 1400))

        val menuItems4 = mutableListOf<MenuItem>()
        menuItems4.add(MenuItem("Cappuccino Grande", 1700))

        val menuItems5 = mutableListOf<MenuItem>()
        menuItems5.add(MenuItem("Latte Chico", 1400))

        val menuItems6 = mutableListOf<MenuItem>()
        menuItems6.add(MenuItem("Latte Grande", 1700))

        val menuItems7 = mutableListOf<MenuItem>()
        menuItems7.add(MenuItem("Cappuccino Vainilla Chico", 1400))

        val menuItems8 = mutableListOf<MenuItem>()
        menuItems8.add(MenuItem("Cappuccino Vainilla Grande", 1700))

        val menuItems9 = mutableListOf<MenuItem>()
        menuItems9.add(MenuItem("Vainilla Chico", 1400))

        val menuItems10 = mutableListOf<MenuItem>()
        menuItems10.add(MenuItem("Vainilla Grande", 1700))

        val menuItems11 = mutableListOf<MenuItem>()
        menuItems11.add(MenuItem("Milo Chico", 1400))
        val menuItems12 = mutableListOf<MenuItem>()
        menuItems12.add(MenuItem("Milo Grande", 1700))

        val menuItems13 = mutableListOf<MenuItem>()
        menuItems13.add(MenuItem("Mocaccino Chico", 1400))
        val menuItems14 = mutableListOf<MenuItem>()
        menuItems14.add(MenuItem("Mocaccino Grande", 1700))

        val menuItems15 = mutableListOf<MenuItem>()
        menuItems15.add(MenuItem("Tradicional Expresso Chico", 1400))

        val menuItems16 = mutableListOf<MenuItem>()
        menuItems16.add(MenuItem("Tradicional Expreso Grande", 1700))

        val menuItems17 = mutableListOf<MenuItem>()
        menuItems17.add(MenuItem("Tradicional Chico", 1400))
        val menuItems18 = mutableListOf<MenuItem>()
        menuItems18.add(MenuItem("Tradicional Grande", 1700))

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
        menuItems.addAll(menuItems10)
        menuItems.addAll(menuItems11)
        menuItems.addAll(menuItems12)
        menuItems.addAll(menuItems13)
        menuItems.addAll(menuItems14)
        menuItems.addAll(menuItems15)
        menuItems.addAll(menuItems16)
        menuItems.addAll(menuItems17)
        menuItems.addAll(menuItems18)

        val adapter = AlmuerzosAdapter(this, menuItems)
        val listView = findViewById<ListView>(R.id.lvTeCafe)
        listView.adapter = adapter
        Log.d("MenuTeCafe", "Número de elementos en la lista: ${menuItems.size}")
    }
}