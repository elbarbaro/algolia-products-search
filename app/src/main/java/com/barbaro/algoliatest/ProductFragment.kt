package com.barbaro.algoliatest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.algolia.instantsearch.core.connection.ConnectionHandler
import com.algolia.instantsearch.helper.android.list.autoScrollToStart
import com.algolia.instantsearch.helper.android.searchbox.SearchBoxViewAppCompat
import com.algolia.instantsearch.helper.android.searchbox.connectView

class ProductFragment : Fragment() {

    private val connection =  ConnectionHandler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productListView = view.findViewById<RecyclerView>(R.id.productList)
        val searchView = view.findViewById<SearchView>(R.id.searchView)

        val viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]

        val adapterProduct = ProductAdapter()
        viewModel.products.observe(viewLifecycleOwner, Observer { hits -> adapterProduct.submitList(hits) })
        productListView.let {
            it.itemAnimator = null
            it.adapter = adapterProduct
            it.layoutManager = LinearLayoutManager(requireContext())
            it.autoScrollToStart(adapterProduct)
        }

        val searchBoxView = SearchBoxViewAppCompat(searchView)

        connection += viewModel.searchBox.connectView(searchBoxView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        connection.clear()
    }
}