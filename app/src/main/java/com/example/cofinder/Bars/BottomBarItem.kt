package com.example.cofinder.Bars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

data class BarItem(val name:String,
                         val icon:ImageVector,
                         val route:String)

object BottomBarItems{
    val BarItems = listOf(
        BarItem(
            name="Home",
            icon= Icons.Default.Home,
            route = "Home"
        ),
        BarItem(
            name="My Team",
            icon= Icons.Default.Diversity3,
            route = "MyTeams"
        ),
        BarItem(
            name="Schedule",
            icon= Icons.Default.CalendarMonth,
            route = "Schedule"
        ),
        BarItem(
            name="Menu",
            icon= Icons.Default.Menu,
            route = "Menu"
        )
    )
}