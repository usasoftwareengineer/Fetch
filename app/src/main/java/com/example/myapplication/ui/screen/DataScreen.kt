package com.example.myapplication.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.myapplication.compositionlocal.NavControllerLocal
import com.example.myapplication.ui.navigation.Error
import com.example.myapplication.viewmodel.MyViewModel

@Composable
fun DataScreen(
    viewModel: MyViewModel,
    navigateToLoading: () -> Unit,
    navigateToMain: () -> Unit
) {
    val navController = NavControllerLocal.LocalNav.current
    val itemListViewData by viewModel.fetchItemList().collectAsState(initial = null)
    val isLoading by viewModel.isLoading.collectAsState(initial = true)
    LaunchedEffect(itemListViewData) {
        if (isLoading) {
            navigateToLoading()
        } else {
            itemListViewData?.items?.let {
                navigateToMain()
            } ?: itemListViewData?.error?.let {
                navController.navigate(Error(it))
            } ?: navController.navigate(Error("No items and no error"))
        }
    }
}