package com.example.casino.ComidaRapida


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import com.example.casino.Almuerzos.MenuItem
import com.example.casino.Frutas.MenuItemFrutas
import com.example.casino.R

class ComidaRapidaAdapter(private val context: Context, private val menuItems: List<MenuItemComidaRapida>) : BaseAdapter() {

    override fun getCount(): Int {
        return menuItems.size
    }

    override fun getItem(position: Int): Any {
        return menuItems[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.menu_item_layout, parent, false)
        val menuItem = getItem(position) as MenuItem

        val nameTextView = view.findViewById<TextView>(R.id.menuItemNameTextView)
        val priceTextView = view.findViewById<TextView>(R.id.menuItemPriceTextView)
        val quantityTextView = view.findViewById<TextView>(R.id.menuItemQuantityTextView)

        nameTextView.text = menuItem.name
        priceTextView.text = "$${menuItem.price}"
        quantityTextView.text = menuItem.quantity.toString()

        val incrementButton = view.findViewById<AppCompatImageButton>(R.id.incrementButton)
        val decrementButton = view.findViewById<AppCompatImageButton>(R.id.decrementButton)

        incrementButton.setOnClickListener {
            menuItem.quantity++
            notifyDataSetChanged()
        }

        decrementButton.setOnClickListener {
            if (menuItem.quantity > 0) {
                menuItem.quantity--
                notifyDataSetChanged()
            }
        }

        return view
    }
}