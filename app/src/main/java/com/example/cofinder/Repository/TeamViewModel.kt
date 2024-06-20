package com.example.cofinder.Repository

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cofinder.Data.PostData
import com.example.cofinder.Data.TeamData
import com.example.cofinder.Data.UserData
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

    private var _selectedTeam = MutableStateFlow<TeamData>(TeamData())
    val selectedTeam = _selectedTeam.asStateFlow()

    //이 함수는 selectedTeam변수 값을 정하는 함수로 db와 통신하지 않아 위에 적음
    fun selectTeamInfo(team: TeamData) {
        _selectedTeam.value = team
    }

    private var _teamList = MutableStateFlow<List<TeamData>>(emptyList())
    val teamList = _teamList.asStateFlow()

    private var _postList = MutableStateFlow<List<PostData>>(emptyList())
    val postList = _postList.asStateFlow()

    private val _team = MutableStateFlow<TeamData?>(null)
    val team = _team.asStateFlow()

    init {
        getAllTeams()
        getAllPosts()
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

    fun insertPost(teamData: TeamData, postData: PostData) {
        viewModelScope.launch {
            repository.insertPost(teamData, postData)
        }
    }

    fun DeletePost(Team: TeamData) {
        viewModelScope.launch {
            repository.DeleteTeam(Team)
        }
    }

    fun InsertSchedule(Team: TeamData) {
        viewModelScope.launch {
            repository.InsertTeam(Team)
        }
    }

    fun DeleteSchedule(Team: TeamData) {
        viewModelScope.launch {
            repository.DeleteTeam(Team)
        }
    }

    fun addUser(user: UserData, team: TeamData){
        viewModelScope.launch{
            repository.addUser(user, team)
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
                _teamList.value = it
            }
        }
    }

    fun getAllPosts() {
        viewModelScope.launch {
            repository.getAllPosts().collect {
                _postList.value = it
            }
        }
    }

}