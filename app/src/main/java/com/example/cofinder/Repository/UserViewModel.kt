package com.example.cofinder.Repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cofinder.Data.ScheduleData
import com.example.cofinder.Data.TeamData
import com.example.cofinder.Data.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class UserViewModel (private val repository: UserRepository) : ViewModel(){

    private var _UserList = MutableStateFlow<List<UserData>>(emptyList())
    val UserList = _UserList.asStateFlow()

    private val _user = MutableStateFlow<UserData?>(null)
    val user = _user.asStateFlow()

    private val _registeredId = MutableStateFlow(false)
    val registeredId = _registeredId.asStateFlow()

    private val _myTeam = MutableStateFlow<List<TeamData>>(emptyList())
    val myTeam = _myTeam.asStateFlow()

    var login = mutableStateOf(false)

    init {
        getAllUsers()
    }

    fun insertUser(user: UserData) {
        Log.w("UserViewModel", "InsertUser function is called")
        viewModelScope.launch {
            Log.w("UserViewModel", "Attempting to insert user")
            try {
                repository.InsertUser(user)
                Log.w("UserViewModel", "User inserted successfully")
            } catch (e: Exception) {
                Log.e("UserViewModel", "Failed to insert user", e)
            }
        }
    }

    fun UpdateUser(user: UserData) {
        viewModelScope.launch {
            repository.UpdateUser(user)
        }
    }

    fun DeleteUser(user: UserData) {
        viewModelScope.launch {
            repository.DeleteUser(user)
        }
    }

//    fun findUser(user: String) {
//        viewModelScope.launch {
//            repository.findUser(user).collect {
//                _UserList.value = it
//            }
//        }
//    }

    fun getAllUsers() {
        viewModelScope.launch {
            repository.getAllUsers().collect {
                _UserList.value = it
            }
        }
    }

    fun getAllMyTeams() {
        viewModelScope.launch {
            repository.getAllMyteams().collect {
                _myTeam.value = user.value!!.projects
            }
        }
    }

    fun userLogin(userId: String, password: String) {
        viewModelScope.launch {
            val userInfo = repository.userLogin(userId, password)
            _user.value = userInfo
        }
    }

    fun userCheck(userId: String) {
        viewModelScope.launch {
            val registered = repository.userCheck(userId)
            _registeredId.value = registered
        }
    }

    fun addSchedule(userId: String, scheduleData: ScheduleData){
        viewModelScope.launch{
            val scheduleAddedUser = repository.addSchedule(userId,scheduleData)
            _user.value = scheduleAddedUser
        }
    }

    fun deleteSchedule(userId: String, scheduleData: ScheduleData){
        viewModelScope.launch{
            val scheduleAddedUser = repository.deleteSchedule(userId,scheduleData)
            _user.value = scheduleAddedUser
        }
    }

    fun addTeam(userId: String, teamData: TeamData){
        viewModelScope.launch{
            val teamAddedUser = repository.addTeams(userId,teamData)
            _user.value = teamAddedUser
        }
    }
}