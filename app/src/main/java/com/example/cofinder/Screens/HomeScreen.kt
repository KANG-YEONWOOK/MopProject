package com.example.cofinder.Screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
    LaunchedEffect(Unit) {
        teamViewModel.getAllTeams()
    }

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
                        .weight(0.8f)
                        .height(60.dp)
                        .align(Alignment.CenterVertically),
                    shape = RoundedCornerShape(20.dp),
                    maxLines = 1,
                    label = { Text("검색") }
                )
                Button(
                    onClick = { Log.d("userNow", "${userViewModel.user.value}") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.darkgreen)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .weight(0.2f)
                        .height(60.dp)
                        .padding(0.dp, 10.dp, 0.dp, 0.dp),
                ) {
                    Text("검색")
                }
            }
            TeamList(teams = teamViewModel.teamList, userdatas!!)
        }

    }

    if(showDialog){
        NewTeamDialog(
            onDismiss = { showDialog = false },
            teamViewModel,
            userViewModel
        )
    }
}

@Composable
fun NewTeamDialog(onDismiss: () -> Unit, teamViewModel: TeamViewModel, userViewModel: UserViewModel) {
    var name by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var maxMembers by remember { mutableStateOf("") }
    var studyOrProject by remember { mutableStateOf(false) }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colorResource(id = R.color.middlegreen),
        unfocusedBorderColor = colorResource(id = R.color.darkgreen),
        cursorColor = colorResource(id = R.color.darkgreen),
        focusedContainerColor = colorResource(id = R.color.lightlightgreen),
        unfocusedContainerColor = colorResource(id = R.color.white),
        focusedTextColor = colorResource(id = R.color.darkgreen),
        unfocusedTextColor = colorResource(id = R.color.darkgreen)
    )

    val switchColor = SwitchDefaults.colors(
        checkedThumbColor = colorResource(id = R.color.darkgreen),
        checkedTrackColor = colorResource(id = R.color.highlightgreen),
        checkedBorderColor = colorResource(id = R.color.darkgreen),
        checkedIconColor = Color.White,
        uncheckedThumbColor = colorResource(id = R.color.darkgreen),
        uncheckedTrackColor = colorResource(id = R.color.highlightgreen),
        uncheckedBorderColor = colorResource(id = R.color.darkgreen),
        uncheckedIconColor = Color.White
    )

    val buttonColor1 = ButtonDefaults.buttonColors(
        containerColor = colorResource(id = R.color.darkgreen),
        contentColor = Color.White,
        disabledContainerColor = colorResource(id = R.color.middlegreen),
        disabledContentColor = Color.White
    )

    val buttonColor2 = ButtonDefaults.buttonColors(
        containerColor = colorResource(id = R.color.greengray),
        contentColor = Color.White,
        disabledContainerColor = colorResource(id = R.color.greengray),
        disabledContentColor = Color.White
    )

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("새 팀 만들기",style = Typography.titleMedium,
            color = colorResource(id = R.color.darkgreen)) },
        text = {
            Column(modifier = Modifier.height(300.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("팀 이름",style = Typography.bodyLarge,
                        color = colorResource(id = R.color.greengray)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    colors = textFieldColors
                )
                OutlinedTextField(
                    value = subject,
                    onValueChange = { subject = it },
                    placeholder = { Text("과목",style = Typography.bodyLarge,
                        color = colorResource(id = R.color.greengray)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    colors = textFieldColors
                )
                OutlinedTextField(
                    value = maxMembers,
                    onValueChange = { maxMembers = it },
                    placeholder = { Text("최대 인원",style = Typography.bodyLarge,
                        color = colorResource(id = R.color.greengray)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    colors = textFieldColors
                )
                Text("팀 타입",style = Typography.bodyLarge,
                    color = colorResource(id = R.color.darkgreen),
                    modifier = Modifier.padding(top = 12.dp, bottom = 6.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Text("스터디",
                        style = Typography.bodyMedium,
                        color = colorResource(id = R.color.darkgreen),
                        modifier = Modifier.padding(4.dp))
                    Switch(checked = studyOrProject,
                        onCheckedChange = {studyOrProject = !studyOrProject},
                        colors = switchColor,
                        modifier = Modifier.padding(8.dp,6.dp))
                    Text("프로젝트",
                        style = Typography.bodyMedium,
                        color = colorResource(id = R.color.darkgreen),
                        modifier = Modifier.padding(4.dp))
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val scheduleType = if(studyOrProject) Type.PROJECT else Type.STUDY
                    val newTeam = TeamData(
                        (0..Long.MAX_VALUE).random(), // Random ID for the team
                        name = name, // Example name, replace with actual data
                        maxNumber = maxMembers.toInt(),
                        type = scheduleType
                    )
                    newTeam.addUser(userViewModel.user.value!!)
                    teamViewModel.InsertTeam(newTeam)
                    Log.d("DB", "팀 생성")
//                    userViewModel.
//                    userViewModel.user.value!!.projects.add(newTeam)
                    //유저의 프로젝트 목록에 해당 팀의 id를 넣어야함!!
                },colors = buttonColor1

            ) {
                Text("생성")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() },
                colors = buttonColor2) {
                Text("취소")
            }
        },
        containerColor = colorResource(id = R.color.highlightgreen)
    )
}