package com.example.cofinder.Compose

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cofinder.Navigation.Routes
import com.example.cofinder.R

@Composable
fun BottomBar(navController: NavController) {
    NavigationBar(containerColor = Color.White){
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        BottomBarItems.BarItems.forEach { navItem ->
            val selectedColor =
                if(currentRoute == navItem.route) colorResource(id = R.color.lightgreen)
                else colorResource(id = R.color.darkgreen)

            NavigationBarItem(selected = false,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(Routes.Home.route) {
                            saveState = true
                            inclusive = false
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = navItem.icon,
                        contentDescription = navItem.name,
                        tint = selectedColor
                    )
                },
                label={
                    Text(text = navItem.name)
                }
            )
        }
    }

}