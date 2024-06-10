package com.example.cofinder.Repository

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cofinder.Data.TeamData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeamViewModelFactory(private val repository: TeamRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamViewModel::class.java)) {
            return TeamViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class TeamViewModel (private val repository: TeamRepository) : ViewModel(){

    var selectedTeam = mutableStateOf(TeamData())

    private var _TeamList = MutableStateFlow<List<TeamData>>(emptyList())
    val TeamList = _TeamList.asStateFlow()

    init {
        getAllTeams()
    }

    fun InsertTeam(Team: TeamData) {
        viewModelScope.launch {
            repository.InsertTeam(Team)
        }
    }

    fun UpdateTeam(Team: TeamData) {
        viewModelScope.launch {
            repository.UpdateTeam(Team)
        }
    }

    fun DeleteTeam(Team: TeamData) {
        viewModelScope.launch {
            repository.DeleteTeam(Team)
        }
    }

//    fun findTeam(Team: String) {
//        viewModelScope.launch {
//            repository.findTeam(Team).collect {
//                _TeamList.value = it
//            }
//        }
//    }

    fun getAllTeams() {
        viewModelScope.launch {
            repository.getAllTeams().collect {
                _TeamList.value = it
            }
        }
    }
}