package com.example.cofinder.Data

class TeamData(
    var TeamID: Long,
    var name: String,
    var maxNumber: Int,
    var type: Type,
    var users: MutableList<UserData> = mutableListOf(),
    var post: MutableList<PostData> = mutableListOf(),
    var schedule: MutableList<ScheduleData> = mutableListOf(),
) {
    // 기본값을 사용하여 주 생성자를 호출
    constructor(TeamID: Long, name: String, maxNumber: Int, type: Type) :
            this(TeamID, name, maxNumber, type, mutableListOf(), mutableListOf(), mutableListOf())

    constructor() :
            this(TeamID = 0L, name = "", maxNumber = 0, type = Type.PROJECT)
    fun addUser(user: UserData) {
        users.add(user)
    }
}