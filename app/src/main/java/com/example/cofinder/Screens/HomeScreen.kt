package com.example.cofinder.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.cofinder.Navigation.Routes
import com.example.cofinder.R
import com.example.cofinder.Teams.Team
import com.example.cofinder.Teams.TeamList
import com.example.cofinder.Teams.Type
import com.example.cofinder.Teams.User
import com.example.cofinder.ui.theme.Typography

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    val teams = listOf(
        Team(1, "모프 완전정복", Type.STUDY, "모바일 프로그래밍", 5, User(1, "앨리스")),
        Team(2, "기말 프로젝트", Type.PROJECT, "모바일 프로그래밍", 10, User(2, "밥"))
    )
    Scaffold(topBar = { Text(text = "CO-Finder",
        modifier = Modifier.padding(16.dp),
        style = Typography.titleMedium,
        color = colorResource(id = R.color.darkgreen)) })
    {
        Column(modifier = Modifier.padding(it)) {
            TeamList(teams = teams)
        }

    }
}