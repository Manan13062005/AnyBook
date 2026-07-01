package com.example.anybook.data.model

enum class BookingStatus {
    CONFIRMED, COMPLETED, CANCELLED
}

data class Booking(
    val id: String,
    val businessName: String,
    val serviceType: String,
    val date: String,
    val time: String,
    val duration: String,
    val status: BookingStatus,
    val bookingRef: String,
    val price: String = "₹500"
)