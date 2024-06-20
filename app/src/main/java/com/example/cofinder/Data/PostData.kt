package com.example.cofinder.Data

data class PostData(
    val title: String = "",
    val contents: String = "",
    val timestamp: Long = System.currentTimeMillis()
) {
    constructor() : this("title", "contents")
}
