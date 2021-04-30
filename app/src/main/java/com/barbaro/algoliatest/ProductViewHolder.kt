package com.barbaro.algoliatest

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val txtProductName: TextView

    init {
        txtProductName = view.findViewById(R.id.productName)
    }

    fun bind(product: Product) {
       txtProductName.text = product.name
    }
}