package com.example.cofinder.Data

import com.example.cofinder.Teams.Type

class TeamData(
    var Teamid: Long,
    var name: String,
    var maxNumber: Int,
    var type: Type,
    var post:  MutableList<PostData> = mutableListOf()
) {
    constructor() : this(0, "noninfo", 4, Type.PROJECT)
}
