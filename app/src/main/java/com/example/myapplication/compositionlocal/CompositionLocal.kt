package com.example.myapplication.compositionlocal

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

object NavControllerLocal {
    val LocalNav = staticCompositionLocalOf<NavHostController> {
        error("No NavController provided")
    }
}