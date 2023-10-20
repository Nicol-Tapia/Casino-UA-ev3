package com.example.casino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.casino.Menus.MenuAlmuerzo
import com.example.casino.Menus.MenuBebidas
import com.example.casino.Menus.MenuComidaRapida
import com.example.casino.Menus.MenuFrutas
import com.example.casino.Menus.MenuGalletas
import com.example.casino.Menus.MenuPostres
import com.example.casino.Menus.MenuSandwich
import com.example.casino.Menus.MenuTeCafe
import com.example.casino.databinding.ActivityMenuBinding
import com.example.ejemplosmenu.Fragment.HomeFragment
import com.example.ejemplosmenu.Fragment.SettingFragment

class Menu : AppCompatActivity() {
    private lateinit var menuBebidaButton: AppCompatButton
    private lateinit var menupostre: AppCompatButton
    private lateinit var menualmuerzos: AppCompatButton
    private lateinit var menufrutas: AppCompatButton
    private lateinit var menucomidarapida: AppCompatButton
    private lateinit var menugalletas: AppCompatButton
    private lateinit var menusandwich: AppCompatButton
    private lateinit var menutecafe: AppCompatButton
    private lateinit var binding: ActivityMenuBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        menuBebidaButton = findViewById(R.id.menubebida)
        menuBebidaButton.setOnClickListener {
            val intent = Intent(this, MenuBebidas::class.java)
            startActivity(intent)
        }
        menupostre = findViewById(R.id.menupostre)
        menupostre.setOnClickListener {
            val intent = Intent(this, MenuPostres::class.java)
            startActivity(intent)
        }
        menualmuerzos = findViewById(R.id.menualmuerzos)
        menualmuerzos.setOnClickListener {
            val intent = Intent(this, MenuAlmuerzo::class.java)
            startActivity(intent)
        }
        menufrutas = findViewById(R.id.menufrutas)
        menufrutas.setOnClickListener {
            val intent = Intent(this, MenuFrutas::class.java)
            startActivity(intent)
        }
        menucomidarapida = findViewById(R.id.menucomidarapida)
        menucomidarapida.setOnClickListener {
            val intent = Intent(this, MenuComidaRapida::class.java)
            startActivity(intent)
        }
        menugalletas = findViewById(R.id.menugalletas)
        menugalletas.setOnClickListener {
            val intent = Intent(this, MenuGalletas::class.java)
            startActivity(intent)
        }
        menusandwich = findViewById(R.id.menusandwich)
        menusandwich.setOnClickListener {
            val intent = Intent(this, MenuSandwich::class.java)
            startActivity(intent)
        }
        menutecafe = findViewById(R.id.menutecafe)
        menutecafe.setOnClickListener {
            val intent = Intent(this, MenuTeCafe::class.java)
            startActivity(intent)
        }

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //reemplaza el frame x home
        replaceFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener{
            when(it.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_setting -> replaceFragment(SettingFragment())
                else -> {}
            }
            true
        }
    }
    private fun replaceFragment(Fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, Fragment)

        fragmentTransaction.commit()
    }
    }
}