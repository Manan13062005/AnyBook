package com.example.anybook.data.repository

import com.example.anybook.data.model.Booking
import com.example.anybook.data.model.BookingStatus

/**
 * Mock data repository for Booking objects
 * Used for development/portfolio demo purposes
 */
object MockBookingRepository {

    /**
     * Returns a list of mock bookings with different statuses
     */
    fun getMockBookings(): List<Booking> = listOf(
        Booking(
            id = "1",
            businessName = "XYZ Salon, Delhi",
            serviceType = "Haircut",
            date = "Wed, 10 Jul 2024",
            time = "2:30 PM",
            duration = "1 hour",
            status = BookingStatus.CONFIRMED,
            bookingRef = "BK00012345",
            price = "₹500"
        ),
        Booking(
            id = "2",
            businessName = "ABC Gym, New Delhi",
            serviceType = "Membership Renewal",
            date = "Fri, 05 Jul 2024",
            time = "10:00 AM",
            duration = "30 min",
            status = BookingStatus.COMPLETED,
            bookingRef = "BK00012344",
            price = "₹2000"
        ),
        Booking(
            id = "3",
            businessName = "Delhi Dentist Clinic",
            serviceType = "Dental Checkup",
            date = "Sat, 12 Jul 2024",
            time = "4:00 PM",
            duration = "45 min",
            status = BookingStatus.CONFIRMED,
            bookingRef = "BK00012346",
            price = "₹800"
        ),
        Booking(
            id = "4",
            businessName = "Spa & Wellness, Delhi",
            serviceType = "Full Body Massage",
            date = "Sun, 07 Jul 2024",
            time = "11:00 AM",
            duration = "2 hours",
            status = BookingStatus.CANCELLED,
            bookingRef = "BK00012343",
            price = "₹1500"
        )
    )

    /**
     * Creates a single mock booking for success screen demo
     */
    fun createMockBooking(): Booking = Booking(
        id = "5",
        businessName = "Premium Salon, Delhi",
        serviceType = "Hair Coloring",
        date = "Tue, 15 Jul 2024",
        time = "3:00 PM",
        duration = "2 hours",
        status = BookingStatus.CONFIRMED,
        bookingRef = "BK00012347",
        price = "₹1000"
    )

    /**
     * Fetches a specific booking by ID
     */
    fun getMockBookingById(id: String): Booking? {
        return getMockBookings().find { it.id == id }
    }
}