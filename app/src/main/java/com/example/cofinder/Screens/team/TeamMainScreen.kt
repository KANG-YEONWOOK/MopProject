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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.cofinder.Repository.UserViewModel
import com.example.cofinder.ui.theme.Typography
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TeamMainScreen(navController: NavController, userViewModel: UserViewModel, teamViewModel: TeamViewModel) {
    Scaffold(
        topBar = { TopBar(navController) }
    ) {
        TeamMainScreenContent(navController, contentPadding = it, userViewModel, teamViewModel)
    }
}

@Composable
fun TeamMainScreenContent(navController: NavController, contentPadding: PaddingValues, userViewModel: UserViewModel, teamViewModel: TeamViewModel) {
    LaunchedEffect(Unit){
        userViewModel.getAllMyTeams()
    }

    Column(modifier = Modifier.padding(contentPadding),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "나의 팀", style = Typography.titleMedium, color = colorResource(id = R.color.darkgreen), modifier = Modifier.padding(12.dp))
        TeamList2(teams = userViewModel.myTeam, navController = navController, teamViewModel = teamViewModel)
    }
}


@Composable
fun TeamList2(teams: StateFlow<List<TeamData>>, navController: NavController, teamViewModel: TeamViewModel) {
    val teamList by teams.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(teamList) { team ->
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
            teamViewModel.selectTeamInfo(team)
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
                Text(text = "${team.users.size}/${team.maxNumber}", fontSize = 16.sp)
            }
        }
    }
}