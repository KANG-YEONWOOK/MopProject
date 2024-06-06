package com.example.cofinder.Compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cofinder.Navigation.Routes
import com.example.cofinder.R
import com.example.cofinder.ui.theme.Typography

@Composable
fun RegisterTopBar(navController: NavController) {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        IconButton(onClick={navController.navigate(Routes.Login.route){
            popUpTo(0) {
                inclusive = true
            }
            launchSingleTop = true
        } }){
            Icon(Icons.Default.ArrowBackIosNew,null, tint = colorResource(id = R.color.darkgreen))
        }
        Text("Co-Finder",
            modifier = Modifier.padding(top=12.dp, end = 16.dp),
            style = Typography.titleMedium,
            color = colorResource(id = R.color.darkgreen))
    }

}