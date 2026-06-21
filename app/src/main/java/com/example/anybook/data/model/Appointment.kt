package com.example.anybook.data.model


import java.time.LocalDateTime

data class Appointment(
    val id: String,
    val clientId: String,
    val providerId: String,
    val service: String,
    val dateTime: LocalDateTime,
    val durationMinutes: Int,
    val status: AppointmentStatus = AppointmentStatus.SCHEDULED
)

enum class AppointmentStatus { SCHEDULED, COMPLETED, CANCELLED }
