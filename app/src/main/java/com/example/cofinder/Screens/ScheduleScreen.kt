package com.example.cofinder.Screens

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cofinder.Bars.TopBar
import com.example.cofinder.Data.ScheduleData
import com.example.cofinder.Navigation.GlobalViewModel
import com.example.cofinder.R
import com.example.cofinder.Teams.Type
import com.example.cofinder.ui.theme.Typography
import java.util.Locale

@Composable
fun ScheduleScreen(navController: NavController, globalViewModel:GlobalViewModel) {
    Scaffold(topBar = {TopBar(navController = navController)}) {
        ScheduleScreenContent(globalViewModel,contentPadding = it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreenContent(globalViewModel: GlobalViewModel, contentPadding: PaddingValues) {
    val schedules = listOf(
        ScheduleData(date=1718755200000, hour = 12, min = 8, schedulename="모바일프로그래밍 스터디", type= Type.STUDY, subject="모바일프로그래밍"),
        ScheduleData(date=1718755200000, hour = 16, min = 12, schedulename="산학협력프로젝트 팀플", type=Type.PROJECT, subject="산학협력프로젝트")
    ) //6월 19일 클릭하면 확인할 수 있음
    var scheduleNow = listOf<ScheduleData>()

    var scheduleName by remember { mutableStateOf("") }
    var subjectName by remember { mutableStateOf("") }
    var studyOrProject by remember { mutableStateOf(false) }

    val dateFormatter = DatePickerFormatter()
    val datePickerState = rememberDatePickerState()

    var selectedDate by remember{mutableStateOf<Long?>(null)}
    val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault())
    var formattedDate : String

    var expanded by remember { mutableStateOf(false) }

    val evCardColors = CardDefaults.elevatedCardColors(
        containerColor = colorResource(id = R.color.highlightgreen)
    )
    val evCardElevation = CardDefaults.elevatedCardElevation(
        defaultElevation = 0.dp
    )

    val timePickerState = rememberTimePickerState()

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colorResource(id = R.color.lightgreen),
        unfocusedBorderColor = colorResource(id = R.color.darkgreen),
        cursorColor = colorResource(id = R.color.darkgreen),
        focusedContainerColor = Color.White,
        unfocusedContainerColor = colorResource(id = R.color.highlightgreen),
        focusedTextColor = colorResource(id = R.color.darkgreen),
        unfocusedTextColor = colorResource(id = R.color.darkgreen)
    )

    val timePickerColor = TimePickerDefaults.colors(
        clockDialColor= colorResource(id = R.color.highlightgreen),
        clockDialSelectedContentColor = colorResource(id = R.color.white),
        clockDialUnselectedContentColor = colorResource(id = R.color.lightgreen),
        selectorColor= colorResource(id = R.color.middlegreen),
        containerColor = colorResource(id = R.color.highlightgreen),
        periodSelectorBorderColor= colorResource(id = R.color.darkgreen),
        periodSelectorSelectedContainerColor = colorResource(id = R.color.lightgreen),
        periodSelectorUnselectedContainerColor= Color.Transparent,
        periodSelectorSelectedContentColor= colorResource(id = R.color.darkgreen),
        periodSelectorUnselectedContentColor= colorResource(id = R.color.middlegreen),
        timeSelectorSelectedContainerColor= colorResource(id = R.color.white),
        timeSelectorUnselectedContainerColor= colorResource(id = R.color.lightgreen),
        timeSelectorSelectedContentColor= colorResource(id = R.color.middlegreen),
        timeSelectorUnselectedContentColor= colorResource(id = R.color.darkgreen)
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

    LaunchedEffect(datePickerState.selectedDateMillis){
        datePickerState.selectedDateMillis?.let{
            selectedDate = it
        }
    }
    
    val datePickerColor = DatePickerDefaults.colors(
        containerColor = colorResource(id = R.color.highlightgreen),
        dayContentColor = colorResource(id = R.color.darkgreen),
        yearContentColor = colorResource(id = R.color.darkgreen),
        weekdayContentColor = colorResource(id = R.color.darkgreen),
        selectedDayContentColor = Color.White,
        selectedDayContainerColor = colorResource(id = R.color.middlegreen),
        todayDateBorderColor = colorResource(id = R.color.greengray),
        todayContentColor = colorResource(id = R.color.middlegreen)
    )

    LazyColumn(modifier = Modifier
        .padding(contentPadding)
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        item{
            DatePicker(
                state = datePickerState,
                title = {},
                headline = {Text("나의 스케줄",
                    modifier = Modifier
                        .padding(16.dp),
                    style = Typography.titleMedium,
                    color = colorResource(id = R.color.darkgreen))},
                showModeToggle = false,
                dateFormatter = dateFormatter,
                colors = datePickerColor
            )
        }
        if(selectedDate ==null){
            item{
                Text("일정을 확인할 날짜를 선택해주세요.",
                    color = colorResource(id = R.color.darkgreen),
                    style = Typography.bodyLarge,
                    modifier = Modifier.padding(16.dp))
            }
        }else{
            item{
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    selectedDate?.let {
                        formattedDate = dateFormat.format(it)
                        Text("${formattedDate}의 일정",
                            color = colorResource(id = R.color.darkgreen),
                            style = Typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    ElevatedCard(onClick = { expanded = !expanded },
                        modifier = Modifier.fillMaxWidth(),
                        colors = evCardColors,
                        elevation = evCardElevation) {
                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()) {
                            Text("일정 추가",modifier=Modifier.padding(8.dp),
                                style = Typography.bodyLarge,
                                color = colorResource(id = R.color.darkgreen))
                        }
                        if (expanded) {
                            Column(modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                OutlinedTextField(scheduleName, onValueChange = {scheduleName = it},
                                    modifier = Modifier.padding(12.dp),
                                    placeholder = { Text(text = "일정 제목을 입력하세요",
                                        color= colorResource(id = R.color.greengray),
                                        style = Typography.bodyMedium)},
                                    colors = textFieldColors,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                OutlinedTextField(subjectName, onValueChange = {subjectName = it},
                                    modifier = Modifier.padding(12.dp),
                                    placeholder = { Text(text = "모임 주제를 입력하세요",
                                        color= colorResource(id = R.color.greengray),
                                        style = Typography.bodyMedium) },
                                    colors = textFieldColors,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                TimeInput(state = timePickerState,
                                    modifier = Modifier.padding(8.dp),
                                    colors = timePickerColor)
                                Log.i("timestate","${timePickerState.hour},${timePickerState.minute}")
                                Row(modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center) {
                                    Text("스터디",
                                        style = Typography.bodySmall,
                                        color = colorResource(id = R.color.darkgreen),
                                        modifier = Modifier.padding(4.dp))
                                    Switch(checked = studyOrProject,
                                        onCheckedChange = {studyOrProject = !studyOrProject},
                                        colors = switchColor)
                                    Text("프로젝트",
                                        style = Typography.bodySmall,
                                        color = colorResource(id = R.color.darkgreen),
                                        modifier = Modifier.padding(4.dp))
                                }
                                ScheduleAddButton(globalViewModel, selectedDate, timePickerState, scheduleName, subjectName, studyOrProject)
                            }
                        }
                    }
                }
            }
            //selectedDate에 저장되어있는 일정 중
            //날짜에 맞는거 불러와서 렌더링하는거 추가해야함
        }
        scheduleNow = schedules.filter { it-> it.date == selectedDate }
        items(scheduleNow) {
            ScheduleCard(it)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleAddButton(globalViewModel: GlobalViewModel, scheduleNow: Long?, timePickerState: TimePickerState, scheduleName:String, subjectName:String, type: Boolean) {
    val buttonColor = ButtonDefaults.buttonColors(
        containerColor = colorResource(id = R.color.darkgreen),
        contentColor = Color.White,
        disabledContainerColor = colorResource(id = R.color.middlegreen),
        disabledContentColor = Color.White
    )

    Button(modifier = Modifier
        .padding(12.dp)
        .width(120.dp),
        colors = buttonColor,
        onClick = {
            val scheduleType = if(type) Type.STUDY else Type.PROJECT
            val newSchedule = ScheduleData(
                date = scheduleNow,
                hour = timePickerState.hour,
                min = timePickerState.minute,
                schedulename = scheduleName,
                type = scheduleType,
                subject = subjectName
            )
            globalViewModel.userData.schedules?.add(newSchedule)
            Log.i("userdata","${globalViewModel.userData.schedules}")
        }) {
        Text("일정 등록", style = Typography.bodyMedium, modifier = Modifier.padding(6.dp))
    }
}

fun cleanSchedule(){

}