package com.example.cofinder.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cofinder.Bars.BottomBar
import com.example.cofinder.Navigation.GlobalViewModel
import com.example.cofinder.Navigation.MainNavGraph
import com.example.cofinder.Navigation.Routes
import com.example.cofinder.Repository.TeamRepository
import com.example.cofinder.Repository.TeamViewModel
import com.example.cofinder.Repository.TeamViewModelFactory
import com.example.cofinder.Repository.UserRepository
import com.example.cofinder.Repository.UserViewModel
import com.example.cofinder.Repository.UserViewModelFactory
import com.example.cofinder.Screens.login.LoginScreen
import com.google.firebase.Firebase
import com.google.firebase.database.database

@Composable
fun rememberViewModelStoreOwner(): ViewModelStoreOwner {
    val context = LocalContext.current
    return remember(context) { context as ViewModelStoreOwner }
}

val LocalNavGraphViewModelStoreOwner =
    staticCompositionLocalOf<ViewModelStoreOwner> {
        error("Undefined")
    }
@Composable
fun Main(navController: NavHostController) {
    val navStoreOwner = rememberViewModelStoreOwner()
    val context = LocalContext.current

    val userTable = Firebase.database.getReference("Cofinder/user")
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(UserRepository(userTable)))

    val teamTable = Firebase.database.getReference("Cofinder/team")
    val teamViewModel: TeamViewModel = viewModel(factory = TeamViewModelFactory(TeamRepository(teamTable)))

    CompositionLocalProvider(
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {

        Scaffold(bottomBar = {
            if (userViewModel.login.value) //로그인 상태에만 BottomBar렌더링
                BottomBar(navController = navController)
        }) {
            Column(modifier = Modifier.padding(it)) {
                NavHost(
                    navController = navController,
                    startDestination = Routes.Login.route
                ) {
                    composable(Routes.Login.route) {
                        LoginScreen(navController = navController, userViewModel)
                    }

                    MainNavGraph(navController, userViewModel, teamViewModel)
                }
            }
        }
    }
}