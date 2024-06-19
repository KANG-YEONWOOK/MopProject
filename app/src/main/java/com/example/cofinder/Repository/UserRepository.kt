package com.example.cofinder.Repository

import android.util.Log
import com.example.cofinder.Data.ScheduleData
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

class UserRepository(private val table : DatabaseReference) {
    suspend fun InsertUser(userData: UserData) {
        try {
            table.child(userData.studentID).setValue(userData).await()
        } catch (e: Exception) {
            Log.e("UserRepository", "Failed to insert user", e)
        }
    }

    fun UpdateUser(userData: UserData) {
        table.child(userData.studentID.toString())
            .child("studentID").setValue(userData.studentID)
    }

    fun DeleteUser(userData: UserData) {
        table.child(userData.studentID.toString()).removeValue()
    }

    suspend fun getAllUsers(): Flow<List<UserData>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val UserList = mutableListOf<UserData>()
                for(UserSnapshow in snapshot.children) {
                    val User = UserSnapshow.getValue(UserData::class.java)
                    User?.let {
                        UserList.add(it)
                    }
                }
                trySend(UserList)
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

    suspend fun getAllMyteams(): Flow<List<TeamData>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val TeamList = mutableListOf<TeamData>()
                for(UserSnapshow in snapshot.children) {
                    val Team = UserSnapshow.getValue(TeamData::class.java)
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


    suspend fun userLogin(userId: String, password: String): UserData?{
        return try {
            val snapshot = table.child(userId).get().await()
            val user = snapshot.getValue(UserData::class.java)
            if (user != null && user.password == password) {
                Log.d("getuserinfo", "$user")
                user
            } else {
                Log.d("getuserinfo", "else")
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun userCheck(userId: String): Boolean{
        return try {
            val snapshot = table.child(userId).get().await()
            val user = snapshot.getValue(UserData::class.java)
            if (user != null && user.studentID == userId) {
                true
            } else {
                Log.d("getuserinfo", "else")
                false
            }
        } catch (e: Exception) {
            true
        }
    }

    suspend fun addSchedule(userId: String, scheduleData: ScheduleData): UserData?{
        return try {
            val snapshot = table.child(userId).get().await()
            val user = snapshot.getValue(UserData::class.java)

            if (user != null) {
                val schedulesSnapshot = table.child(userId).child("schedules").get().await()
                val schedulesList = schedulesSnapshot.children.mapNotNull { it.getValue(ScheduleData::class.java) }.toMutableList()
                schedulesList.add(scheduleData)
                table.child(userId).child("schedules").setValue(schedulesList).await()
            } else {
                table.child(userId).child("schedules").setValue(listOf(scheduleData)).await()
            }
            return user
        } catch (e: Exception) {
            null
        }
    }

    suspend fun addTeams(userId: String, teamData: TeamData): UserData?{
        return try {
            val snapshot = table.child(userId).get().await()
            val user = snapshot.getValue(UserData::class.java)

            if (user != null) {
                val teamsSnapshot = table.child(userId).child("projects").get().await()
                val teamList = teamsSnapshot.children.mapNotNull { it.getValue(TeamData::class.java) }.toMutableList()
                teamList.add(teamData)
                table.child(userId).child("projects").setValue(teamList).await()
            } else {
                table.child(userId).child("projects").setValue(listOf(teamData)).await()
            }
            return user
        } catch (e: Exception) {
            null
        }
    }

}
