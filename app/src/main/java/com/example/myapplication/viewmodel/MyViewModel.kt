package com.example.myapplication.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.FetchRepository
import com.example.myapplication.transformer.ItemListTransformer
import com.example.myapplication.data.viewdata.GroupedItemListViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * view model which holds item list and status of network request
 */
@HiltViewModel
class MyViewModel @Inject constructor(
    private val fetchRepository: FetchRepository
) : ViewModel() {
    // item list as view data
    private var itemList: StateFlow<GroupedItemListViewData>? = null
    private val itemListTransformer = ItemListTransformer()
    val isLoading = fetchRepository.isLoading()
    val selectedTab = mutableIntStateOf(0)

    fun fetchItemList(): StateFlow<GroupedItemListViewData> {
        itemList?.let {
            return it
        }

        val itemList =
            itemListTransformer.transform(fetchRepository.fetchItemList(), viewModelScope)
        this.itemList = itemList
        return itemList
    }
}