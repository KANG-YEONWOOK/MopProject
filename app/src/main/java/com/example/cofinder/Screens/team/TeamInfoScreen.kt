package com.example.cofinder.Screens.team

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cofinder.Bars.TopBar
import com.example.cofinder.Data.ScheduleData
import com.example.cofinder.R
import com.example.cofinder.Data.Type
import com.example.cofinder.Repository.TeamViewModel
import com.example.cofinder.Repository.UserViewModel
import com.example.cofinder.Screens.schedule.ScheduleCancelButton
import com.example.cofinder.ui.theme.Typography


@Composable
fun TeamInfoScreen(navController: NavController, userViewModel: UserViewModel, teamViewModel: TeamViewModel) {
    Scaffold(
        topBar = { TopBar(navController) }
    ) {
        TeamInfoScreenContent(navController, contentPadding = it)
    }
}

@Composable
fun TeamInfoScreenContent(navController: NavController, contentPadding:PaddingValues) {
    val schedules = listOf(
        ScheduleData(date=1718755200000, hour = 12, min = 8, schedulename="모바일프로그래밍 스터디", type= Type.STUDY, subject="모바일프로그래밍"),
        ScheduleData(date=1718755200000, hour = 16, min = 12, schedulename="산학협력프로젝트 팀플", type=Type.PROJECT, subject="산학협력프로젝트")
    )

    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item{
            //Text("팀이름 여기 들어가야 됨")
        }
        items(schedules) { schedule ->
            TeamScheduleCard(schedule)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScheduleCard( scheduleData:ScheduleData) {

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