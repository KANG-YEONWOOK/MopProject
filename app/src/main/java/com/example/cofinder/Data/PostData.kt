package com.example.cofinder.Data

import com.example.cofinder.Teams.Type

class PostData(
    var Postid: Long,
    var title: String,
    var contents: String
) {
    constructor() : this(0, "noninfo", "noninfo")
}
