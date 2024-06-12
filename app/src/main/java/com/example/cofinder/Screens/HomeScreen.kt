package com.example.cofinder.Screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cofinder.Data.TeamData
import com.example.cofinder.Data.Type
import com.example.cofinder.Data.UserData
import com.example.cofinder.R
import com.example.cofinder.Repository.TeamViewModel
import com.example.cofinder.Repository.UserViewModel
import com.example.cofinder.Screens.team.TeamList
import com.example.cofinder.ui.theme.Typography

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, userViewModel: UserViewModel, teamViewModel: TeamViewModel) {

    val userdatas by userViewModel.user.collectAsState()

    var query by remember{ mutableStateOf("")}
    var showDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(topBar = { Text(text = "CO-Finder",
        modifier = Modifier.padding(16.dp),
        style = Typography.titleMedium,
        color = colorResource(id = R.color.darkgreen)) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = colorResource(id = R.color.darkgreen)
            ) {
                Text(text = "+", color = Color.White)
            }
        })
    {
        Column(modifier = Modifier.padding(it)) {
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp),
                    shape = RoundedCornerShape(20.dp),
                    maxLines = 1,
                    label = { Text("검색") }
                )
                Button(
                    onClick = {  },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.darkgreen)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .height(60.dp)
                        .padding(0.dp, 10.dp, 0.dp, 0.dp),
                ) {
                    Text("검색")
                }
            }
            TeamList(teams = teamViewModel.TeamList, userdatas!!)
        }

    }

    if(showDialog){
        NewTeamDialog(
            onDismiss = { showDialog = false },
            teamViewModel,
            userdatas!!
        )
    }
}

@Composable
fun NewTeamDialog(onDismiss: () -> Unit, teamViewModel: TeamViewModel, user: UserData) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(Type.STUDY) }
    var subject by remember { mutableStateOf("") }
    var maxMembers by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("새 팀 만들기") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("팀 이름") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it },
                    label = { Text("과목") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = maxMembers,
                    onValueChange = { maxMembers = it },
                    label = { Text("최대 인원") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("팀 타입")
                    Row {
                        RadioButton(
                            selected = type == Type.STUDY,
                            onClick = { type = Type.STUDY }
                        )
                        Text("스터디")
                        RadioButton(
                            selected = type == Type.PROJECT,
                            onClick = { type = Type.PROJECT }
                        )
                        Text("프로젝트")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val newTeam = TeamData(
                        (0..Long.MAX_VALUE).random(), // Random ID for the team
                        name = name, // Example name, replace with actual data
                        maxNumber = maxMembers.toInt(),
                        type = type
                    )
                    newTeam.addUser(user)
                    teamViewModel.InsertTeam(newTeam)
                    Log.d("DB", "팀 생성")
                    user.projects.add(newTeam)
                    //유저의 프로젝트 목록에 해당 팀의 id를 넣어야함!!
                },colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.darkgreen)
                )

            ) {
                Text("생성")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.darkgreen)
                )) {
                Text("취소")
            }
        }
    )
}