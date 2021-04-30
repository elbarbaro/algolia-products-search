package com.barbaro.algoliatest

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.algolia.instantsearch.helper.android.filter.facet.FacetListViewHolder
import com.algolia.instantsearch.helper.android.inflate
import com.algolia.search.model.search.Facet

class MyFacetListViewHolder(view: View) : FacetListViewHolder(view){

    val txtFacetName: TextView
    val txtFacetCount: TextView
    val icon: ImageView

    init {
        txtFacetName = view.findViewById(R.id.facetName)
        txtFacetCount = view.findViewById(R.id.facetCount)
        icon = view.findViewById(R.id.icon)
    }

    override fun bind(facet: Facet, selected: Boolean, onClickListener: View.OnClickListener) {
        view.setOnClickListener(onClickListener)
        txtFacetCount.text = facet.count.toString()
        txtFacetCount.visibility = View.VISIBLE
        icon.visibility = if (selected) View.VISIBLE else View.INVISIBLE
        txtFacetName.text = facet.value
    }

    object Factory : FacetListViewHolder.Factory {

        override fun createViewHolder(parent: ViewGroup): FacetListViewHolder {
            return MyFacetListViewHolder(parent.inflate(R.layout.item_facet))
        }
    }
}