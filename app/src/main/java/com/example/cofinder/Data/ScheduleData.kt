package com.example.cofinder.Data

import com.example.cofinder.Teams.Type

data class ScheduleData(
    var date: Long,
    var hour: Int,
    var min: Int,
    var schedulename: String,
    var type: Type,
    var subject: String
) {
    constructor() : this(0, 0,0, "",Type.STUDY,"")
}