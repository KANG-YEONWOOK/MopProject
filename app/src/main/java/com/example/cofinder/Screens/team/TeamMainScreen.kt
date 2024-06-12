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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cofinder.Bars.TopBar
import com.example.cofinder.Data.TeamData
import com.example.cofinder.Navigation.Routes
import com.example.cofinder.R
import com.example.cofinder.Repository.TeamViewModel
import com.example.cofinder.ui.theme.Typography

@Composable
fun TeamMainScreen(navController: NavController, teamViewModel: TeamViewModel) {
    Scaffold(
        topBar = { TopBar(navController) }
    ) {
        TeamMainScreenContent(navController, contentPadding = it, teamViewModel)
    }
}

@Composable
fun TeamMainScreenContent(navController: NavController, contentPadding: PaddingValues, teamViewModel: TeamViewModel) {
//    val post = PostData(1L,"","")
//    val teams = listOf(
//        TeamData(1, "모프 완전정복", 4, Type.PROJECT, listOf(post) , User(1, "앨리스")),
//        TeamData(2, "기말 프로젝트", 6, Type.PROJECT, "", User(2, "밥"))
//    )
    Column(modifier = androidx.compose.ui.Modifier.padding(contentPadding),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "나의 팀", style = Typography.titleMedium, color = colorResource(id = R.color.darkgreen), modifier = Modifier.padding(12.dp))
//        TeamList2(teams = teamViewModel.TeamList.value, navController, teamViewModel)
    }
}


@Composable
fun TeamList2(teams: List<TeamData>, navController: NavController, teamViewModel: TeamViewModel) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(teams) { team ->
            TeamCard2(team, navController, teamViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamCard2(team: TeamData, navController: NavController, teamViewModel: TeamViewModel) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = {
            teamViewModel.selectedTeam.value = team
            navController.navigate(Routes.TeamInfo.route)
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${team.type}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = team.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "현재 팀원수/${team.maxNumber}", fontSize = 16.sp)
            }
        }
    }
}