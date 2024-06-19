package com.example.cofinder.Navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.cofinder.Repository.TeamViewModel
import com.example.cofinder.Repository.UserViewModel
import com.example.cofinder.Screens.HomeScreen
import com.example.cofinder.Screens.login.LoginScreen
import com.example.cofinder.Screens.login.RegisterScreen
import com.example.cofinder.Screens.schedule.ScheduleScreen
import com.example.cofinder.Screens.team.TeamInfoScreen
import com.example.cofinder.Screens.team.TeamMainScreen

fun NavGraphBuilder.MainNavGraph(navController: NavHostController, userViewModel: UserViewModel , teamViewModel: TeamViewModel){

    navigation(startDestination = "Home", route = "Main"){
        composable(route=Routes.Login.route){
            LoginScreen(navController = navController, userViewModel)
        }

        composable(route=Routes.Register.route){
            RegisterScreen(navController = navController, userViewModel)
        }

        composable(route = Routes.Home.route){
            HomeScreen(navController = navController, userViewModel, teamViewModel)
        }

        composable(route = Routes.MyTeam.route){
            TeamMainScreen(navController = navController, userViewModel, teamViewModel)
        }

        composable(route = Routes.Schedule.route){
            ScheduleScreen(navController = navController, userViewModel)
        }

        composable(route = Routes.TeamInfo.route){
            TeamInfoScreen(navController, teamViewModel)
        }
    }

}