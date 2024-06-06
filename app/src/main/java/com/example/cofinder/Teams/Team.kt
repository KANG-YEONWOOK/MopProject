package com.example.cofinder.Teams

data class Team(
    val id: Long,
    val name: String,
    val type: Type,
    val subject: String,
    val maxMembers: Int,
    val leader: User
)

data class User(
    val id: Long,
    val name: String
)

enum class Type {
    STUDY, PROJECT
}
