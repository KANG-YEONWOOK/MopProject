package com.example.cofinder.Navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.cofinder.Screens.HomeScreen
import com.example.cofinder.Screens.LoginScreen
import com.example.cofinder.Screens.MenuScreen
import com.example.cofinder.Screens.RegisterScreen
import com.example.cofinder.Screens.ScheduleScreen
import com.example.cofinder.Screens.TeamInfoScreen
import com.example.cofinder.Screens.TeamMainScreen

fun NavGraphBuilder.MainNavGraph(navController: NavHostController, globalViewModel: GlobalViewModel){

    navigation(startDestination = "Home", route = "Main"){
        composable(route=Routes.Login.route){
            LoginScreen(navController = navController)
        }

        composable(route=Routes.Register.route){
            RegisterScreen(navController = navController)
        }

        composable(route = Routes.Home.route){
            HomeScreen()
        }

        composable(route = Routes.MyTeam.route){
            TeamMainScreen(navController = navController)
        }

        composable(route = Routes.Schedule.route){
            ScheduleScreen(navController = navController, globalViewModel)
        }

        composable(route = Routes.Menu.route){
            MenuScreen(navController = navController)
        }

        composable(route = Routes.TeamInfo.route){
            TeamInfoScreen(navController)
        }
    }


}