package com.example.casino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.casino.BarraNav.MenuFragment
import com.example.casino.Menus.MenuAlmuerzo
import com.example.casino.Menus.MenuBebidas
import com.example.casino.Menus.MenuComidaRapida
import com.example.casino.Menus.MenuFrutas
import com.example.casino.Menus.MenuGalletas
import com.example.casino.Menus.MenuPostres
import com.example.casino.Menus.MenuSandwich
import com.example.casino.Menus.MenuTeCafe
import com.example.casino.databinding.ActivityMenuBinding


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


        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //reemplaza el frame x home
        replaceFragment(MenuFragment())

        binding.bottomNavigation.setOnItemSelectedListener{
            when(it.itemId) {
                R.id.nav_home -> replaceFragment(MenuFragment())
                else -> {}
            }
            true
        }
    }
    private fun replaceFragment(Fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.framelayout, Fragment)

        fragmentTransaction.commit()
    }
}
