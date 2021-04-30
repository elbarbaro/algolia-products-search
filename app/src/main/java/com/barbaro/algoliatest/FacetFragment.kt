package com.barbaro.algoliatest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.algolia.instantsearch.core.connection.ConnectionHandler
import com.algolia.instantsearch.helper.android.filter.facet.FacetListAdapter
import com.algolia.instantsearch.helper.android.list.autoScrollToStart
import com.algolia.instantsearch.helper.filter.facet.connectView

class FacetFragment: Fragment() {

    private val connection = ConnectionHandler()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_facet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val facetListView = view.findViewById<RecyclerView>(R.id.facetList)

        val viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]

        val adapterFacet = FacetListAdapter(MyFacetListViewHolder.Factory)

        facetListView.let {
            it.adapter = adapterFacet
            it.layoutManager = LinearLayoutManager(requireContext())
            it.autoScrollToStart(adapterFacet)
        }

        connection += viewModel.facetList.connectView(adapterFacet, viewModel.facetPresenter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        connection.clear()
    }
}