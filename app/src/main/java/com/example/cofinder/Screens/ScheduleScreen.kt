package com.example.cofinder.Screens

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
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
import com.example.cofinder.Compose.TopBar
import com.example.cofinder.R
import com.example.cofinder.ui.theme.Typography
import java.util.Locale

@Composable
fun ScheduleScreen(navController: NavController) {
    Scaffold(topBar = {TopBar(navController = navController)}) {
        ScheduleScreenContent(contentPadding = it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreenContent(contentPadding: PaddingValues) {
    val dateFormatter = DatePickerFormatter()
    val datePickerState = rememberDatePickerState()

    var selectedDate by remember{mutableStateOf<Long?>(null)}
    val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    var formattedDate : String


    val buttonColor = ButtonDefaults.buttonColors(
        containerColor = colorResource(id = R.color.darkgreen),
        contentColor = Color.White,
        disabledContainerColor = colorResource(id = R.color.middlegreen),
        disabledContentColor = Color.White
    )

    LaunchedEffect(datePickerState.selectedDateMillis){
        datePickerState.selectedDateMillis?.let{
            selectedDate = it
        }
    }

    Column(modifier = Modifier
        .padding(contentPadding)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        DatePicker(
            state = datePickerState,
            title = {},
            headline = {Text("나의 스케줄 확인",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                style = Typography.titleMedium,
                color = colorResource(id = R.color.darkgreen))},
            showModeToggle = false,
            dateFormatter = dateFormatter
        )
        selectedDate?.let {
            formattedDate = dateFormat.format(it)
            Text(formattedDate)
        }
        Row {
            DateConfirmButton()
            DateCancelButton()
        }
    }

}

@Composable
fun DateConfirmButton() {
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
        onClick = { /*비어있음*/ }) {
        Text("일정등록", style = Typography.bodyMedium, modifier = Modifier.padding(6.dp))
    }
}

@Composable
fun DateCancelButton() {
    val buttonColor = ButtonDefaults.buttonColors(
        containerColor = colorResource(R.color.greengray),
        contentColor = Color.White,
        disabledContainerColor = colorResource(id = R.color.greengray),
        disabledContentColor = Color.White
    )
    Button(modifier = Modifier
        .padding(12.dp)
        .width(120.dp),
        colors = buttonColor,
        onClick = { /*비어있음*/ }) {
        Text("일정삭제", style = Typography.bodyMedium, modifier = Modifier.padding(6.dp))
    }
}