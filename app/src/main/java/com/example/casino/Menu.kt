package com.example.casino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.casino.BarraNav.ConfFragment
import com.example.casino.BarraNav.MenuFragment
import com.example.casino.databinding.ActivityMenuBinding


class Menu : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        replaceFragment(MenuFragment())

        binding.bottomNavigation.setOnItemSelectedListener{
            when(it.itemId) {
                R.id.nav_home -> replaceFragment(MenuFragment())
                R.id.nav_setting -> replaceFragment(ConfFragment())
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
