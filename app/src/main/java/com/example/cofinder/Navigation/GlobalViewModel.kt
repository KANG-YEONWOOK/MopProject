package com.example.cofinder.Navigation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cofinder.Data.UserData

class GlobalViewModel : ViewModel() {
    var userID:String? = ""
    var userPasswd:String? = ""
    var userData = userID?.let { userPasswd?.let { it1 -> UserData(it, it1) } }
    var loginStatus = mutableStateOf( false )

}