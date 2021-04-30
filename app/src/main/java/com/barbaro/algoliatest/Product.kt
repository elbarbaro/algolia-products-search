package com.barbaro.algoliatest

import com.algolia.instantsearch.core.highlighting.HighlightedString
import com.algolia.instantsearch.helper.highlighting.Highlightable
import com.algolia.search.model.Attribute
import kotlinx.serialization.json.JsonObject

// Class to model data
data class Product (
    val name: String,
    override val _highlightResult: JsonObject?
): Highlightable {

    public val highlightedName: HighlightedString?
        get() = getHighlight(Attribute("name"))
}