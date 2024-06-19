package com.example.cofinder.Screens.team

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.cofinder.Bars.TopBar
import com.example.cofinder.Data.ScheduleData
import com.example.cofinder.Data.TeamData
import com.example.cofinder.R
import com.example.cofinder.Data.Type
import com.example.cofinder.Navigation.Routes
import com.example.cofinder.Repository.TeamViewModel
import com.example.cofinder.Repository.UserViewModel
import com.example.cofinder.Screens.NewTeamDialog
import com.example.cofinder.Screens.schedule.ScheduleCancelButton
import com.example.cofinder.ui.theme.Typography


@Composable
fun TeamInfoScreen(navController: NavController, teamViewModel: TeamViewModel) {
    Scaffold(
        topBar = { TopBar(navController) }
    ) {
        TeamInfoScreenContent(contentPadding = it, teamViewModel)
    }
}

@Composable
fun TeamInfoScreenContent(contentPadding:PaddingValues, teamViewModel: TeamViewModel) {

    var expanded by remember{ mutableStateOf(false) }

    val teamNow by teamViewModel.selectedTeam.collectAsState()
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            Text(teamNow.name, style = Typography.titleMedium, color = colorResource(id = R.color.darkgreen), modifier = Modifier.padding(8.dp))
            Divider(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp), thickness = 3.dp)
        }
        item{
            Text("게시글", style = Typography.bodyLarge, color = colorResource(id = R.color.darkgreen), modifier = Modifier.padding(12.dp))
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
        }
        if(teamNow.post.isEmpty()){
            item{
                Box(modifier = Modifier
                    .border(1.dp, colorResource(id = R.color.greengray))
                    .padding(12.dp)
                    .background(colorResource(id = R.color.white))
                    .fillMaxWidth()){
                    Column(modifier = Modifier
                        .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("게시글이 없습니다", style = Typography.bodyLarge, color = colorResource(id = R.color.middlegreen), modifier = Modifier.padding(12.dp))
                        PostAddButton(teamViewModel)
                    }

                }
            }
        }else{
            items(teamNow.post){post->
                Box(modifier = Modifier
                    .border(1.dp, colorResource(id = R.color.greengray))
                    .background(colorResource(id = R.color.white))
                    .fillMaxWidth()
                    .clickable { expanded != expanded }){
                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()) {
                        Text(post.title, style = Typography.bodyLarge, color = colorResource(id = R.color.middlegreen), modifier = Modifier.padding(12.dp))
                        if(expanded){
                            Divider(modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp), thickness = 1.dp)
                            Text(post.contents, style = Typography.bodyMedium, color = colorResource(id = R.color.darkgreen), modifier = Modifier.padding(12.dp))
                        }
                        PostAddButton(teamViewModel)
                    }
                }
            }
        }

        items(teamNow.schedule) { schedule ->
            TeamScheduleCard(schedule)
        }
    }
}

@Composable
fun PostAddButton(teamViewModel: TeamViewModel) {

    val buttonColor = ButtonDefaults.buttonColors(
        containerColor = colorResource(id = R.color.darkgreen),
        contentColor = Color.White,
        disabledContainerColor = colorResource(id = R.color.middlegreen),
        disabledContentColor = Color.White
    )

    var showDialog by remember {
        mutableStateOf(false)
    }

    Button(onClick = { showDialog = true },
        modifier = Modifier.padding(8.dp),
        colors = buttonColor){
        Text("게시글 추가", style = Typography.bodyMedium)
    }

    if(showDialog){
        NewPostDialog(
            onDismiss = { showDialog = false },
            teamViewModel
        )
    }
}

@Composable
fun NewPostDialog(onDismiss: () -> Unit, teamViewModel: TeamViewModel) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colorResource(id = R.color.middlegreen),
        unfocusedBorderColor = colorResource(id = R.color.darkgreen),
        cursorColor = colorResource(id = R.color.darkgreen),
        focusedContainerColor = colorResource(id = R.color.lightlightgreen),
        unfocusedContainerColor = colorResource(id = R.color.white),
        focusedTextColor = colorResource(id = R.color.darkgreen),
        unfocusedTextColor = colorResource(id = R.color.darkgreen)
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
        title = { Text("새 게시글 추가", style = Typography.titleMedium,
            color = colorResource(id = R.color.darkgreen)) },
        text = {
            Column(modifier = Modifier.height(300.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("제목", style = Typography.bodyLarge,
                        color = colorResource(id = R.color.greengray)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    colors = textFieldColors
                )
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    placeholder = { Text("내용", style = Typography.bodyLarge,
                        color = colorResource(id = R.color.greengray)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    colors = textFieldColors
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // 게시글 저장 로직을 여기에 추가
                    val teamData = teamViewModel.selectedTeam.value // 팀 데이터를 가져오는 로직을 여기에 추가
                    teamViewModel.InsertPost(teamData, title, content)
                    onDismiss()
                }, colors = buttonColor1

            ) {
                Text("추가")
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScheduleCard( scheduleData:ScheduleData ) {

    var expanded by remember{ mutableStateOf(false) }

    val evCardColors = CardDefaults.elevatedCardColors(
        containerColor = colorResource(id = R.color.highlightgreen)
    )
    val evCardElevation = CardDefaults.elevatedCardElevation(
        defaultElevation = 0.dp
    )

    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        colors = evCardColors,
        elevation = evCardElevation,
        onClick = { expanded = !expanded }) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${scheduleData.type}",modifier=Modifier.padding(8.dp),
                    style = Typography.bodyLarge,
                    color = colorResource(id = R.color.middlegreen)
                )
                Text(scheduleData.schedulename,modifier=Modifier.padding(8.dp),
                    style = Typography.bodyMedium,
                    color = colorResource(id = R.color.darkgreen)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${scheduleData.hour}시 ${scheduleData.min}분 시작",modifier=Modifier.padding(8.dp),
                    style = Typography.bodyMedium,
                    color = colorResource(id = R.color.darkgreen)
                )
                Text(scheduleData.subject,modifier=Modifier.padding(8.dp),
                    style = Typography.bodyMedium,
                    color = colorResource(id = R.color.darkgreen)
                )
            }
            if (expanded) {
                Column(modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    ScheduleCancelButton()
                }
            }
        }
    }
}