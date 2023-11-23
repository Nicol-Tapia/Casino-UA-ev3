package com.example.casino

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.example.casino.Alumnos.MenuEstudiantes
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

class Pagos : AppCompatActivity() {

    private lateinit var totalPriceTextView: TextView
    private lateinit var barcodeImageView: ImageView
    private lateinit var productListTextView: TextView
    private lateinit var menucasa: AppCompatImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagos)

        totalPriceTextView = findViewById(R.id.totalPriceTextView)
        barcodeImageView = findViewById(R.id.barcodeImageView)
        productListTextView = findViewById(R.id.productListTextView)

        menucasa = findViewById(R.id.menucasa)
        menucasa.setOnClickListener {
            val intent = Intent(this, MenuEstudiantes::class.java)
            startActivity(intent)
        }

        // Retrieve total and product list from the intent
        val total = intent.getDoubleExtra("total", 0.0)
        val productList = intent.getParcelableArrayListExtra<Productos>("productos")

        // Display the total price
        totalPriceTextView.text = getString(R.string.total_price_format, total)

        // Display the list of products in a TextView
        val productDetails = productList?.joinToString(separator = "\n") {
            "${it.nombre} - ${it.precio}"
        } ?: "No hay productos"

        productListTextView.text = productDetails

        // Generate and display the barcode
        val barcodeBitmap = generateBarcode(total.toString())
        barcodeImageView.setImageBitmap(barcodeBitmap)
    }

    private fun generateBarcode(data: String): Bitmap {
        val widthPixels = 700
        val heightPixels = 400
        val barcodeFormat = BarcodeFormat.CODE_128

        val multiFormatWriter = MultiFormatWriter()
        val bitMatrix: BitMatrix = multiFormatWriter.encode(data, barcodeFormat, widthPixels, heightPixels)

        val barcodeBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888)
        for (x in 0 until widthPixels) {
            for (y in 0 until heightPixels) {
                barcodeBitmap.setPixel(
                    x,
                    y,
                    if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt()
                )
            }
        }

        return barcodeBitmap
    }

}
