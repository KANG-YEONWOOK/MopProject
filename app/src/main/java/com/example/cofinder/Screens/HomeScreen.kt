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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.cofinder.R
import com.example.cofinder.Teams.Team
import com.example.cofinder.Teams.TeamList
import com.example.cofinder.Teams.Type
import com.example.cofinder.Teams.User
import com.example.cofinder.ui.theme.Typography

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    var teams by remember { mutableStateOf(
        listOf(
            Team(1, "모프 완전정복", Type.STUDY, "모바일 프로그래밍", 5, User(1, "앨리스")),
            Team(2, "기말 프로젝트", Type.PROJECT, "모바일 프로그래밍", 10, User(2, "밥"))
        )) }
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
            TeamList(teams = teams)
        }

    }

    if(showDialog){
        NewTeamDialog(
            onDismiss = { showDialog = false },
            onCreate = { team ->
                teams = teams + team
                showDialog = false
            }
        )
    }
}

@Composable
fun NewTeamDialog(onDismiss: () -> Unit, onCreate: (Team) -> Unit) {
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
                    val newTeam = Team(
                        id = (0..Long.MAX_VALUE).random(),
                        name = name,
                        type = type,
                        subject = subject,
                        maxMembers = maxMembers.toIntOrNull() ?: 0,
                        leader = User(id = 0, name = "User") // 리더 정보는 실제 사용자의 정보로 설정
                    )
                    onCreate(newTeam)
                    Log.d("DB", "팀 생성")
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