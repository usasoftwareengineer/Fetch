package com.example.myapplication.data.viewdata

import com.example.myapplication.data.model.Item

data class GroupedItemListViewData(
    val items: LinkedHashMap<Int, List<Item>>?= null,
    val error: String? = null
) : ViewData