package com.example.myapplication.ui.application

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.compositionlocal.NavControllerLocal
import com.example.myapplication.ui.navigation.AppNavHost
import com.example.myapplication.viewmodel.MyViewModel

/**
 * Top screen of the application
 */
@Composable
fun MyApp(modifier: Modifier) {
    val viewModel: MyViewModel = hiltViewModel()
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AppNavHost(navController = NavControllerLocal.LocalNav.current, modifier = modifier, viewModel = viewModel)
    }
}