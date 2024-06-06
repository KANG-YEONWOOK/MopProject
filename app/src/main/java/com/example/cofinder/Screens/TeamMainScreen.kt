package com.example.cofinder.Screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.cofinder.Compose.TopBar

@Composable
fun TeamMainScreen(navController: NavController) {
    Scaffold(
        topBar = { TopBar(navController) }
    ) {
        TeamMainScreenContent(contentPadding = it)
    }
}

@Composable
fun TeamMainScreenContent(contentPadding: PaddingValues) {

}