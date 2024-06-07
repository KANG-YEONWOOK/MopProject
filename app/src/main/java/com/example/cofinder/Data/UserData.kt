package com.example.cofinder.Data


data class UserData(
    var studentID: String = "default",
    var characterIndex: Int = 0,
    var projects: List<String> = mutableListOf("")
) {
    constructor() : this("default", 0, projects = mutableListOf(""))
}