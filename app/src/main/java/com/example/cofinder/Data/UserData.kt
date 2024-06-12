package com.example.cofinder.Data


data class UserData(
    var studentID: String,
    var password: String,
    var projects: MutableList<TeamData> = mutableListOf(),
    var schedules: MutableList<ScheduleData>? = mutableListOf(),
    var loginStatus: Boolean
) {
    constructor() : this("default", "1234", projects = mutableListOf(), schedules = mutableListOf(), loginStatus = false)

    constructor(studentID: String, password: String) : this(studentID, password, projects = mutableListOf(), schedules = mutableListOf(), loginStatus = false)

}