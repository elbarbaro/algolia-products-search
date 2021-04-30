package com.barbaro.algoliatest

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.algolia.instantsearch.core.connection.ConnectionHandler
import com.algolia.instantsearch.helper.android.list.SearcherSingleIndexDataSource
import com.algolia.instantsearch.helper.android.searchbox.SearchBoxConnectorPagedList
import com.algolia.instantsearch.helper.searcher.SearcherSingleIndex
import com.algolia.instantsearch.helper.stats.StatsConnector
import com.algolia.search.client.ClientSearch
import com.algolia.search.model.APIKey
import com.algolia.search.model.ApplicationID
import com.algolia.search.model.IndexName
import io.ktor.client.features.logging.LogLevel
import kotlinx.serialization.json.jsonPrimitive

class MyViewModel : ViewModel() {

    private val applicationId = "latency"
    private val apiKey = "3d9875e51fbd20c7754e65422f7ce5e1"
    private val indexName = "bestbuy"

    // Search initialization

    val client = ClientSearch(ApplicationID(applicationId), APIKey(apiKey), LogLevel.ALL)
    val index = client.initIndex(IndexName(indexName))
    val searcher = SearcherSingleIndex(index)


    // Hits initialization
    val dataSourceFactory = SearcherSingleIndexDataSource.Factory(searcher) { hit ->
        Product(
            hit.json.getValue("name").jsonPrimitive.content
        )
    }

    val pagedListConfig = PagedList.Config.Builder().setPageSize(50).build()
    val products: LiveData<PagedList<Product>> = LivePagedListBuilder(
            dataSourceFactory, pagedListConfig
        ).build()

    val searchBox = SearchBoxConnectorPagedList(searcher, listOf(products))
    val stats = StatsConnector(searcher)
    val connection = ConnectionHandler()

    init {
        connection += searchBox
        connection += stats
    }

    override fun onCleared() {
        super.onCleared()
        searcher.cancel()
        connection.clear()
    }
}