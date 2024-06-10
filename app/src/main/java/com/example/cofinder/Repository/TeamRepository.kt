package com.example.cofinder.Repository;

import com.example.cofinder.Data.TeamData
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await

class TeamRepository(private val table :DatabaseReference) {
    suspend fun InsertTeam(teamData: TeamData) {
        table.child(teamData.TeamID.toString()).setValue(teamData).await()
    }

    fun UpdateTeam(teamData: TeamData) {
        table.child(teamData.TeamID.toString())
            .child("TeamID").setValue(teamData.TeamID)
    }

    fun DeleteTeam(teamData: TeamData) {
        table.child(teamData.TeamID.toString()).removeValue()
    }
}
