package com.example.cofinder.Screens.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.cofinder.Data.ScheduleData
import com.example.cofinder.R
import com.example.cofinder.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleCard(scheduleData: ScheduleData) {

    var expanded by remember { mutableStateOf(false) }

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
                    color = colorResource(id = R.color.middlegreen))
                Text("${scheduleData.schedulename}",modifier=Modifier.padding(8.dp),
                    style = Typography.bodyMedium,
                    color = colorResource(id = R.color.darkgreen))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${scheduleData.hour}시 ${scheduleData.min}분 시작",modifier=Modifier.padding(8.dp),
                    style = Typography.bodyMedium,
                    color = colorResource(id = R.color.darkgreen))
                Text("${scheduleData.subject}",modifier=Modifier.padding(8.dp),
                    style = Typography.bodyMedium,
                    color = colorResource(id = R.color.darkgreen))
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

@Composable
fun ScheduleCancelButton() {
    val buttonColor = ButtonDefaults.buttonColors(
        containerColor = colorResource(R.color.middlegreen),
        contentColor = Color.White,
        disabledContainerColor = colorResource(id = R.color.middlegreen),
        disabledContentColor = Color.White
    )
    Button(modifier = Modifier
        .padding(12.dp)
        .width(120.dp),
        colors = buttonColor,
        onClick = { /*비어있음*/ }) {
        Text("일정 삭제", style = Typography.bodyMedium, modifier = Modifier.padding(6.dp))
    }
}