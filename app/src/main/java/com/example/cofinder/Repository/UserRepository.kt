package com.example.cofinder.Repository

import com.example.cofinder.Data.UserData
import com.google.firebase.database.DatabaseReference
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
}
