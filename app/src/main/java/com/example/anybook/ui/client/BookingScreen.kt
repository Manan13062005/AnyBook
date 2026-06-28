package com.example.anybook.ui.client

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp

data class BookingScreenCallbacks(
    val onNameChange: (String) -> Unit,
    val onPhoneChange: (String) -> Unit,
    val onDateSelected: (String) -> Unit,
    val onTimeSelected: (String) -> Unit,
    val onNextClick: () -> Unit,
    val onBackClick: () -> Unit
)

@Composable
fun BookingScreen(
    businessName: String,
    currentName: String = "",
    currentPhone: String = "",
    currentDate: String = "",
    currentTime: String = "",
    callbacks: BookingScreenCallbacks
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .paddingVertical(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = callbacks.onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = businessName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(androidx.compose.foundation.rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Date Selection
            Text(
                text = "Select Date",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier.paddingVertical(8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5), shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                    .clickable { callbacks.onDateSelected(currentDate) }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color(0xFF1565C0),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = currentDate.ifEmpty { "Pick a date" },
                    fontSize = 14.sp,
                    color = if (currentDate.isEmpty()) Color.Gray else Color.Black,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Time Selection
            Text(
                text = "Select Time",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier.paddingVertical(8.dp)
            )

            val timeSlots = listOf(
                "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM",
                "11:00 AM", "11:30 AM", "2:00 PM", "2:30 PM",
                "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM"
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(timeSlots) { slot ->
                    Button(
                        onClick = { callbacks.onTimeSelected(slot) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentTime == slot) Color(0xFF1565C0) else Color(0xFFF5F5F5)
                        ),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = slot,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (currentTime == slot) Color.White else Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Name Input
            Text(
                text = "Your Name",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier.paddingVertical(8.dp)
            )

            TextField(
                value = currentName,
                onValueChange = callbacks.onNameChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                placeholder = {
                    Text("Enter your name", color = Color.Gray)
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Phone Input
            Text(
                text = "Phone Number",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier.paddingVertical(8.dp)
            )

            TextField(
                value = currentPhone,
                onValueChange = callbacks.onPhoneChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                placeholder = {
                    Text("+91 98765 43210", color = Color.Gray)
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        // Next Button
        Button(
            onClick = callbacks.onNextClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1565C0)
            ),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
            enabled = currentName.isNotEmpty() && currentPhone.isNotEmpty() && currentDate.isNotEmpty() && currentTime.isNotEmpty()
        ) {
            Text(
                text = "Next",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}

private fun Modifier.paddingVertical(value: Dp) = this.padding(vertical = value)

@Preview(showBackground = true)
@Composable
fun BookingScreenPreview() {
    BookingScreen(
        businessName = "Hair Salon",
        currentName = "",
        currentPhone = "",
        currentDate = "Mon, 30 Jun",
        currentTime = "2:00 PM",
        callbacks = BookingScreenCallbacks(
            onNameChange = { },
            onPhoneChange = { },
            onDateSelected = { },
            onTimeSelected = { },
            onNextClick = { },
            onBackClick = { }
        )
    )
}