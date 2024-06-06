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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cofinder.Navigation.Routes
import com.example.cofinder.R

@Composable
fun TopBar(navController: NavController) {
    val fontFamily = FontFamily(
        fonts = listOf(
            Font(R.font.gmarket_sans_ttf_medium, FontWeight.Medium),
            Font(R.font.gmarket_sans_ttf_bold, FontWeight.Bold),
            Font(R.font.gmarket_sans_ttf_light, FontWeight.Light)
        )
    )
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        IconButton(onClick={navController.navigate(Routes.Home.route){
            popUpTo(Routes.Home.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        } }){
            Icon(Icons.Default.ArrowBackIosNew,null)
        }
        Text("Co-Finder",
            modifier = Modifier.padding(top=16.dp, end = 16.dp),
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp)
    }

//홈으로 돌아가는 뒤로가기 버튼
//    Scaffold (topBar = {
//        TopBar(navController = navController)
//    }){
//        //화면구성
//    }
//이런식으로 사용

}