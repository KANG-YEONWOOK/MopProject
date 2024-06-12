package com.example.cofinder.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cofinder.Bars.BottomBar
import com.example.cofinder.Navigation.GlobalViewModel
import com.example.cofinder.Navigation.MainNavGraph
import com.example.cofinder.Navigation.Routes
import com.example.cofinder.Repository.UserRepository
import com.example.cofinder.Repository.UserViewModel
import com.example.cofinder.Repository.UserViewModelFactory
import com.example.cofinder.Screens.login.LoginScreen
import com.google.firebase.Firebase
import com.google.firebase.database.database


@Composable
fun Main(navController: NavHostController) {

    val context = LocalContext.current
    val usertable = Firebase.database.getReference("cofnider/user")
    val userviewModel: UserViewModel = viewModel(factory = UserViewModelFactory(UserRepository(usertable)))

    val teamtable = Firebase.database.getReference("cofnider/team")
    val teamviewModel: UserViewModel = viewModel(factory = UserViewModelFactory(UserRepository(teamtable)))

    val globalViewModel = GlobalViewModel()

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

                MainNavGraph(navController, globalViewModel)
            }
        }
    }
}