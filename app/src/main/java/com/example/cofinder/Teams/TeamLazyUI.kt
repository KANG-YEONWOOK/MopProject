package com.example.cofinder.Teams

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.cofinder.R


@Composable
fun TeamList(teams: List<Team>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(teams) { team ->
            TeamCard(team)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamCard(team: Team) {
    var expanded by remember { mutableStateOf(false) }

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
                Text(text = "현재 팀원수/${team.maxMembers}", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(text = "${team.name}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = "${team.subject}", fontSize = 16.sp)
            }


            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* 여기서 팀 참가 로직 처리 */ },
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