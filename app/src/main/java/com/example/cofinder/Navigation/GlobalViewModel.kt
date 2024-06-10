package com.example.cofinder.Navigation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cofinder.Data.UserData
import com.example.cofinder.Teams.Type

class GlobalViewModel : ViewModel() {
    var userData = UserData("0000")
    var userID:String? = ""
    var userPasswd:String? = ""
    var loginStatus = mutableStateOf( false )

    var scheduleName = mutableStateOf("")
    var subjectName = mutableStateOf("")
    var studyOrProject = mutableStateOf(false)
    var type: Type? = null

}