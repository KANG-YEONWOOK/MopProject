package com.example.cofinder.Repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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

    init {
        getAllUsers()
    }

    fun InsertUser(User: UserData) {
        viewModelScope.launch {
            repository.InsertUser(User)
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
}