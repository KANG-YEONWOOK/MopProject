package com.example.cofinder.Repository

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
        table.child(userData.studentID.toString()).setValue(userData).await()
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

    suspend fun getUserInfo(userId: String, password: String): UserData?{
        return try {
            val snapshot = table.child(userId).get().await()
            val user = snapshot.getValue(UserData::class.java)
            if (user != null && user.password == password) {
                user
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

}
