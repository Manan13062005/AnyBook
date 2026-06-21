package com.example.anybook.data.model

data class Owner(
    val userId: String,
    val businessIds: List<String> = emptyList() // businesses this owner manages
)
