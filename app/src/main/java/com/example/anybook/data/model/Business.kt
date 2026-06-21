package com.example.anybook.data.model

data class Business(
    val id: String,
    val ownerId: String, // links to Owner.userId
    val name: String,
    val category: String,
    val address: String,
    val rating: Float = 0f,
    val reviewCount: Int = 0
)
