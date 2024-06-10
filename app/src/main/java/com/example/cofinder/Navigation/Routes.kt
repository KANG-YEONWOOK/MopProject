package com.example.cofinder.Navigation

sealed class Routes(val route: String) {

    object Register: Routes("Register")

    object Login: Routes("Login")

    object Home: Routes("Home")

    object MyTeam: Routes("MyTeams")

    object Schedule: Routes("Schedule")

    object Menu: Routes("Menu")

    object TeamInfo: Routes("TeamInfoScreen")
}