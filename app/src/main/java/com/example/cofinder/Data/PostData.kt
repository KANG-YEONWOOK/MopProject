package com.example.cofinder.Data

class PostData(
    val title: String = "",
    val contents: String = "",
    val timestamp: Long = System.currentTimeMillis()
) {
    constructor() : this("title", "contents")
}
