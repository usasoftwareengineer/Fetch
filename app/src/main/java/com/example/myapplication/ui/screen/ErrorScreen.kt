package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Error screen with message
 */
@Composable
fun ErrorScreen(error: String) {
    Text(
        text = "An error occurred: $error",
        modifier = Modifier.padding(16.dp)
    )
}