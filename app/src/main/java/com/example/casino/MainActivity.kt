package com.example.casino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btniniciosesion = findViewById<AppCompatButton>(R.id.btniniciosesion)
        btniniciosesion.setOnClickListener {
            val intent = Intent(this, InicioSesion::class.java)
            startActivity(intent)
            Log.e("InicioSesion", "entro a la vista")
        }
        val btnregistro = findViewById<TextView>(R.id.btnregistro)
        btnregistro.setOnClickListener {
            val intent = Intent(this, RegistroUsuario::class.java)
            startActivity(intent)
        }

    }
}