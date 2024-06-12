package com.example.cofinder.Data

class PostData(
    var Postid: Long,
    var title: String,
    var contents: String
) {
    constructor() : this(0, "noninfo", "noninfo")
}
