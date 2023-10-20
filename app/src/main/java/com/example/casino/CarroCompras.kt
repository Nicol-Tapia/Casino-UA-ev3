package com.example.casino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageButton

class CarroCompras : AppCompatActivity() {

    private lateinit var menucasa: AppCompatImageButton
    private lateinit var pagar: AppCompatImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro_compras)

        menucasa = findViewById(R.id.menucasa)
        menucasa.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }
        pagar = findViewById(R.id.pagar)
        pagar.setOnClickListener {
            val intent = Intent(this, Pago::class.java)
            startActivity(intent)
        }
    }
}