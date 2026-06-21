package com.example.anybook.data.model

data class User(
    val id: String,
    val name: String,
    val phone: String,
    val email: String? = null,
    val role: UserRole
)

enum class UserRole { CLIENT, OWNER }