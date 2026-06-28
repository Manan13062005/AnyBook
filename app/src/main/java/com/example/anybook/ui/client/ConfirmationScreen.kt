package com.example.anybook.ui.client

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import androidx.compose.material3.Surface
import androidx.compose.ui.tooling.preview.Preview

data class ConfirmationScreenCallbacks(
    val onConfirmClick: () -> Unit,
    val onCancelClick: () -> Unit
)

@Composable
fun ConfirmationScreen(
    businessName: String,
    selectedDate: String,
    selectedTime: String,
    customerName: String,
    customerPhone: String,
    callbacks: ConfirmationScreenCallbacks
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Content
        Column(modifier = Modifier.weight(1f)) {
            // Business Name
            Text(
                text = businessName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.paddingVertical(8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Date & Time (Highlighted)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5), shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Booking Details",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = selectedDate,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Text(
                    text = selectedTime,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1565C0)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Customer Details
            Column {
                Text(
                    text = "Your Details",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.paddingVertical(8.dp)
                )

                DetailRow("Name", customerName)
                DetailRow("Phone", customerPhone)
            }
        }

        // Buttons
        Column(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = callbacks.onConfirmClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1565C0)
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Confirm Booking",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = callbacks.onCancelClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF5F5F5)
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .paddingVertical(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}

private fun Modifier.paddingVertical(value: Dp) = this.padding(vertical = value)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ConfirmationScreenPreview() {
    Surface(color = Color.White) {
        ConfirmationScreen(
            businessName = "Elite Hair Salon",
            selectedDate = "Monday, 30 June",
            selectedTime = "2:00 PM - 2:30 PM",
            customerName = "Manan Kumar",
            customerPhone = "+91 98765 43210",
            callbacks = ConfirmationScreenCallbacks(
                onConfirmClick = { /* Preview action */ },
                onCancelClick = { /* Preview action */ }
            )
        )
    }
}