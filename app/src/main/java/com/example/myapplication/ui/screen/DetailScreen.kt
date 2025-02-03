package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.data.model.Item
import com.example.myapplication.viewmodel.MyViewModel

@Composable
fun DetailScreen(listId: Int, itemId: Int, viewModel: MyViewModel, modifier: Modifier = Modifier) {
    val itemListViewData by viewModel.fetchItemList().collectAsState(initial = null)
    var item: Item? = null
    itemListViewData?.let { viewData ->
        item = viewData.items?.get(listId)?.find { it.id == itemId } ?: return
    }
    item?.let {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "List ID : ${it.listId}")
            Text(text = "ID : ${it.id}")
            Text(text = "Name : ${it.name}")
        }
    } ?: Text("This shouldn't happen")
}