package com.example.myapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.myapplication.ui.screen.DataScreen
import com.example.myapplication.ui.screen.DetailScreen
import com.example.myapplication.ui.screen.ErrorScreen
import com.example.myapplication.ui.screen.LoadingScreen
import com.example.myapplication.ui.screen.MainScreen
import com.example.myapplication.viewmodel.MyViewModel
import kotlinx.serialization.Serializable

@Serializable
object Data

@Serializable
object Main

@Serializable
object Loading

@Serializable
data class Error(val error: String)

@Serializable
data class Detail(val listId: Int, val itemId: Int)

/**
 * Navigation host
 */
@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier, viewModel: MyViewModel) {
    NavHost(
        navController = navController,
        startDestination = Data
    ) {
        composable<Data> {
            DataScreen(
                viewModel = viewModel,
                navigateToLoading = { navController.navigate(Loading) },
                navigateToMain = { navController.navigate(Main) })
        }
        composable<Main> {
            MainScreen(
                modifier = modifier,
                viewModel = viewModel
            )
        }
        composable<Loading> {
            LoadingScreen()
        }
        composable<Error> {
            val error: Error = it.toRoute()
            ErrorScreen(error.error)
        }
        composable<Detail> {
            val detail: Detail = it.toRoute()
            DetailScreen(detail.listId, detail.itemId, viewModel = viewModel)
        }
    }
}