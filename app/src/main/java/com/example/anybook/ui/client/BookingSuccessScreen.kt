package com.example.anybook.ui.client

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anybook.data.model.Booking
import com.example.anybook.data.model.BookingStatus

@Composable
fun BookingSuccessScreen(
    booking: Booking,
    onViewBooking: () -> Unit,
    onCancelBooking: () -> Unit
) {
    val scale = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(1f, animationSpec = tween(600))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Success Icon
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = Color(0xFF4CAF50),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Success",
                tint = Color.White,
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "BOOKING CONFIRMED",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Order #${booking.bookingRef}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Receipt Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFFAFAFA),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(20.dp)
        ) {
            // Divider
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            // Business Location
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("📍", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = booking.businessName,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Service
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("💇", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = booking.serviceType,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("📅", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = booking.date,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🕐", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "${booking.time} • ${booking.duration}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("💰", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = booking.price,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Divider
            Divider()
            Spacer(modifier = Modifier.height(16.dp))

            // Confirmation Email
            Text(
                text = "Confirmation email sent to:",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "user@email.com",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1565C0)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Divider
            Divider()
        }

        Spacer(modifier = Modifier.height(32.dp))

        // View Booking Button
        Button(
            onClick = onViewBooking,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
        ) {
            Text(
                text = "View My Booking",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Cancel Booking Button
        Button(
            onClick = onCancelBooking,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE))
        ) {
            Text(
                text = "Cancel Booking",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFD32F2F)
            )
        }
    }
}

@Composable
fun Divider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xFFDDDDDD))
    )
}

@Preview(showBackground = true)
@Composable
fun BookingSuccessScreenPreview() {
    BookingSuccessScreen(
        booking = Booking(
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
        onViewBooking = {},
        onCancelBooking = {}
    )
}