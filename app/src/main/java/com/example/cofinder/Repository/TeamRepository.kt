package com.example.cofinder.Repository;

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


    suspend fun insertPost(teamData: TeamData, postData: PostData): TeamData? {
        return try {
            val teamid = teamData.TeamID
            val snapshot = table.child(teamid.toString()).get().await()
            val team = snapshot.getValue(TeamData::class.java)

            if (team != null) {
//                val post = PostData(title, contents)
//                val postList = team.post.toMutableList()
//                postList.add(post)
                table.child(teamid.toString()).child("posts").setValue(postData.timestamp).await()
            } else {
//                val postList = listOf(PostData(title, contents))
                table.child(teamid.toString()).child("posts").setValue(postData.timestamp).await()
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

    suspend fun addUser(user: UserData, team: TeamData): UserData? {
        return try {
            val userInTeamsSnapshot = table.child(team.TeamID.toString()).child("users").get().await()
            val userList = userInTeamsSnapshot.children.mapNotNull { it.getValue(UserData::class.java) }.toMutableList()

            if(!userList.any{it.studentID == user.studentID}){
                userList.add(user)
                table.child(team.TeamID.toString()).child("users").setValue(userList).await()
            }
            return user
        } catch (e: Exception) {
            null
        }
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

    suspend fun getAllPosts(): Flow<List<PostData>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val allPostsList = mutableListOf<PostData>()
                for (teamSnapshot in snapshot.children) {
                    val postsSnapshot = teamSnapshot.child("posts")
                    for (postSnapshot in postsSnapshot.children) {
                        val post = postSnapshot.getValue(PostData::class.java)
                        post?.let {
                            allPostsList.add(it)
                        }
                    }
                }
                trySend(allPostsList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error if needed
                close(error.toException())
            }
        }
        table.addValueEventListener(listener)
        awaitClose {
            table.removeEventListener(listener)
        }
    }


}
