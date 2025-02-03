package com.example.myapplication.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.compositionlocal.NavControllerLocal
import com.example.myapplication.data.model.Item
import com.example.myapplication.data.model.ItemList
import com.example.myapplication.ui.navigation.Detail
import com.example.myapplication.viewmodel.MyViewModel

@Composable
fun MainScreen(modifier: Modifier, viewModel: MyViewModel) {
    val itemListViewData by viewModel.fetchItemList().collectAsState(initial = null)
    val lazyListState = rememberLazyListState()

    itemListViewData?.items?.let {
        GroupedItemListView(it, lazyListState, viewModel, modifier)
    }
    // Scroll to top when tab changes
    LaunchedEffect(viewModel.selectedTab.intValue) {
        lazyListState.scrollToItem(0)
    }
}

/**
 * A view holding tab and lists of items
 */
@Composable
fun GroupedItemListView(
    list: LinkedHashMap<Int, List<Item>>,
    lazyListState: LazyListState,
    viewModel: MyViewModel,
    modifier: Modifier
) {
    Column {
        TabLayoutView(
            groupIds = list.keys,
            selectedTab = viewModel.selectedTab,
            modifier = modifier
        )
        ItemListView(
            list = list,
            selectedTab = viewModel.selectedTab,
            modifier = modifier,
            lazyListState = lazyListState
        )
    }
}

/**
 * Scrollable tab filled with listId
 */
@Composable
fun TabLayoutView(groupIds: Set<Int>, selectedTab: MutableIntState, modifier: Modifier = Modifier) {
    ScrollableTabRow(selectedTabIndex = selectedTab.intValue, modifier) {
        groupIds.forEachIndexed { index, groupId ->
            Tab(selected = selectedTab.intValue == index, onClick = {
                selectedTab.intValue = index
            }, text = {
                Text(text = groupId.toString())
            })
        }
    }
}

/**
 * Scrollable view holding item list for each tab
 */
@Composable
fun ItemListView(
    list: LinkedHashMap<Int, List<Item>>,
    selectedTab: MutableIntState,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState
) {
    val groupIds = list.keys.toList()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxSize(),
        state = lazyListState
    ) {
        list[groupIds[selectedTab.intValue]]?.forEach { item ->
            item {
                ItemView(item = item)
            }
        }
    }
}

/**
 * each item view
 */
@Composable
fun ItemView(item: Item, modifier: Modifier = Modifier) {
    val navController = NavControllerLocal.LocalNav.current

    Surface(
        modifier = modifier
            .clickable { navController.navigate(Detail(item.listId, item.id)) }
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = item.id.toString(), modifier = modifier.width(50.dp))
            Text(text = item.name ?: "", modifier = modifier.wrapContentSize())
        }
    }
}

@Preview
@Composable
fun PreviewItemList() {
    CompositionLocalProvider(NavControllerLocal.LocalNav provides rememberNavController()) {
        ItemListView(
            list = linkedMapOf(
                1 to listOf(
                    Item(id = 12, listId = 1, name = "Item 12"),
                    Item(id = 123, listId = 1, name = "Item 123"),
                    Item(id = 14, listId = 1, name = "Item 14")
                ),
                2 to listOf(Item(id = 2, listId = 2, name = "Item 2"))
            ),
            selectedTab = remember { mutableIntStateOf(0) },
            lazyListState = rememberLazyListState()
        )
    }
}

@Preview
@Composable
fun PreviewItemView() {
    CompositionLocalProvider(NavControllerLocal.LocalNav provides rememberNavController()) {
        ItemView(
            Item(id = 1, listId = 1, name = "Preview Item")
        )
    }
}