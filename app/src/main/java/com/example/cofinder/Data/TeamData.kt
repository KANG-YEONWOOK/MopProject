package com.example.cofinder.Data

import com.example.cofinder.Teams.Type

class TeamData(
    var TeamID: Long,
    var name: String,
    var maxNumber: Int,
    var type: Type,
    var post:  MutableList<PostData> = mutableListOf(),
    var schedule: MutableList<ScheduleData> = mutableListOf(),
) {
    constructor() : this(0, "noninfo", 4, Type.PROJECT)
}
