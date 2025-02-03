package com.example.myapplication.transformer

import com.example.myapplication.data.model.Item
import com.example.myapplication.data.model.ItemList
import com.example.myapplication.repository.Resource
import com.example.myapplication.data.viewdata.GroupedItemListViewData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.collections.LinkedHashMap

/**
 * Transforms ItemList into GroupedItemListViewData
 */
class ItemListTransformer : Transformer<ItemList, GroupedItemListViewData>() {

    override fun transform(
        input: Flow<Resource<ItemList>>,
        scope: CoroutineScope
    ): StateFlow<GroupedItemListViewData> {
        return input.map { resource ->
            when (resource) {
                is Resource.Success -> {
                    GroupedItemListViewData(items = createItems(resource.data.items))
                }

                is Resource.Failure -> GroupedItemListViewData(error = resource.e.toString())
                is Resource.Loading -> GroupedItemListViewData()
            }
        }.stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = GroupedItemListViewData()
        )
    }

    /**
     * filter, sort and group items
     */
    internal fun createItems(items: List<Item>): LinkedHashMap<Int, List<Item>> {
        return LinkedHashMap(
            items.filter { !it.name.isNullOrEmpty() }
                .sortedWith(compareBy<Item> { it.listId }.thenBy { extractNumber(it.name) })
                .groupBy { it.listId }
        )
    }

    /**
     * extract from the last until it's not a digit
     */
    internal fun extractNumber(name: String?): Int {
        if (name == null) return Int.MAX_VALUE
        val numberPart = name.takeLastWhile { it.isDigit() }
        return numberPart.toIntOrNull() ?: Int.MAX_VALUE
    }
}