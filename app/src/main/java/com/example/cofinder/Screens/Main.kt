package com.example.cofinder.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cofinder.Compose.BottomBar
import com.example.cofinder.Navigation.MainNavGraph
import com.example.cofinder.Navigation.Routes


@Composable
fun Main(navController: NavHostController) {

    val context = LocalContext.current

    Scaffold(bottomBar = {
        if(true) //로그인 상태에만 BottomBar렌더링
            BottomBar(navController = navController)}){
        Column(modifier = Modifier.padding(it)) {
            NavHost(
                navController = navController,
                startDestination = Routes.Login.route
            ){
                composable(Routes.Login.route){
                    LoginScreen(navController = navController)
                }

                composable(Routes.Register.route){
                    RegisterScreen(navController)
                }
                MainNavGraph(navController)
            }
        }
    }
}