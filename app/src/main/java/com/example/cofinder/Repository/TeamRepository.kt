package com.example.cofinder.Repository;

import androidx.room.util.copy
import com.example.cofinder.Data.PostData
import com.example.cofinder.Data.TeamData
import com.example.cofinder.Data.UserData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.internal.NopCollector.emit
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


    fun getTeam(teamId: String): Flow<TeamData> = flow {
        val snapshot = table.child(teamId).get().await()
        val team = snapshot.getValue(TeamData::class.java)
        if (team != null) {
            val postsSnapshot = table.child(teamId).child("posts").get().await()
            val posts = postsSnapshot.children.mapNotNull { it.getValue(PostData::class.java) }
            emit(team.copy(posts = posts))
        }
    }

    suspend fun insertPost(teamData: TeamData, title: String, contents: String): TeamData? {
        return try {
            val teamid = teamData.TeamID
            val snapshot = table.child(teamid.toString()).get().await()
            val team = snapshot.getValue(TeamData::class.java)

            if (team != null) {
                val post = PostData(title, contents)
                val postList = team.post.toMutableList()
                postList.add(post)
                table.child(teamid.toString()).child("posts").setValue(postList).await()
            } else {
                val postList = listOf(PostData(title, contents))
                table.child(teamid.toString()).child("posts").setValue(postList).await()
            }
            return team
        } catch (e: Exception) {
            null
        }
    }

    suspend fun InsertSchedule(teamData: TeamData) {

    }

    fun DeletePost(teamData: TeamData) {

    }

    fun DeleteSchedule(teamData: TeamData) {

    }

    suspend fun getAllTeams(): Flow<List<TeamData>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val TeamList = mutableListOf<TeamData>()
                for(TeamSnapshow in snapshot.children) {
                    val Team = TeamSnapshow.getValue(TeamData::class.java)
                    Team?.let {
                        TeamList.add(it)
                    }
                }
                trySend(TeamList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        table.addValueEventListener(listener)
        awaitClose {
            table.removeEventListener(listener)
        }
    }
}
