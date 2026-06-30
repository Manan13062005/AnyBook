package com.example.anybook.ui.client

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anybook.data.model.Booking
import com.example.anybook.data.model.BookingStatus

@Composable
fun MyBookingsScreen(bookings: List<Booking>) {
    if (bookings.isEmpty()) {
        EmptyBookingsState()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "My Bookings",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(8.dp)
                )
            }
            items(bookings) { booking ->
                BookingCard(booking = booking)
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun BookingCard(booking: Booking) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFFAFAFA), shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = booking.businessName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            StatusBadge(status = booking.status)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text("Service:", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Text(booking.serviceType, fontSize = 13.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text("📅 ", fontSize = 13.sp)
            Text(booking.date, fontSize = 13.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text("🕐 ", fontSize = 13.sp)
            Text("${booking.time} • ${booking.duration}", fontSize = 13.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFE3F2FD), shape = RoundedCornerShape(8.dp))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Ref: ${booking.bookingRef}", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1565C0))
            Text("Copy", fontSize = 11.sp, color = Color(0xFF1565C0), fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun StatusBadge(status: BookingStatus) {
    val (bgColor, textColor) = when (status) {
        BookingStatus.CONFIRMED -> Color(0xFFE8F5E9) to Color(0xFF2E7D32)
        BookingStatus.COMPLETED -> Color(0xFFC8E6C9) to Color(0xFF1B5E20)
        BookingStatus.CANCELLED -> Color(0xFFF5F5F5) to Color(0xFF757575)
    }

    Text(
        text = status.name,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = textColor,
        modifier = Modifier
            .background(bgColor, shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

@Composable
fun EmptyBookingsState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(color = Color(0xFFE0E0E0), shape = RoundedCornerShape(50.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "📅", fontSize = 50.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "No Bookings Yet",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Start by browsing available businesses and make your first appointment!",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Browse Now →",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF1565C0),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyBookingsScreenPreview() {
    MyBookingsScreen(
        bookings = listOf(
            Booking(
                id = "1",
                businessName = "XYZ Salon, Delhi",
                serviceType = "Haircut",
                date = "Wed, 10 Jul 2024",
                time = "2:30 PM",
                duration = "1 hour",
                status = BookingStatus.CONFIRMED,
                bookingRef = "BK00012345"
            ),
            Booking(
                id = "2",
                businessName = "ABC Gym, New Delhi",
                serviceType = "Membership Renewal",
                date = "Fri, 05 Jul 2024",
                time = "10:00 AM",
                duration = "30 min",
                status = BookingStatus.COMPLETED,
                bookingRef = "BK00012344"
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyBookingsScreenPreview() {
    MyBookingsScreen(bookings = emptyList())
}