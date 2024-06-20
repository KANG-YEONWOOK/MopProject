package com.example.cofinder.Screens.team

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cofinder.Data.TeamData
import com.example.cofinder.Data.UserData
import com.example.cofinder.R
import com.example.cofinder.Repository.TeamViewModel
import com.example.cofinder.Repository.UserViewModel
import kotlinx.coroutines.flow.StateFlow


@Composable
fun TeamList(teams: StateFlow<List<TeamData>>, userData: UserData, userViewModel: UserViewModel, teamViewModel: TeamViewModel) {
    val teamList by teams.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(teamList) { team ->
            TeamCard(team, userData, userViewModel, teamViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamCard(team: TeamData, userData: UserData, userViewModel: UserViewModel, teamViewModel: TeamViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${team.type}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = "${team.users.size}/${team.maxNumber}", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(text = team.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = "${team.type}", fontSize = 16.sp)
            }


            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* 여기서 팀 참가 로직 처리 */
                        if(team.maxNumber <= team.users.size){
                            Toast.makeText(context, "해당 팀의 모집은 마감되었습니다!", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            userViewModel.addTeam(userViewModel.user.value!!.studentID,team)
                            teamViewModel.addUser(userViewModel.user.value!!, team)
                            Log.d("DB", "팀 참가")
                        } },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.darkgreen)
                    )
                ) {
                    Text("팀 참가")
                }
            }
        }
    }
}