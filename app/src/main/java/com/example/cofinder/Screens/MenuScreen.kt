package com.example.cofinder.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.cofinder.Compose.TopBar

@Composable
fun MenuScreen(navController: NavController) {
    Scaffold(topBar = {TopBar(navController)}) {
        MenuScreenContent(contentPadding = it)
    }
}

@Composable
fun MenuScreenContent(contentPadding: PaddingValues) {
    //로그아웃, 회원탈퇴
    Column {

    }
}