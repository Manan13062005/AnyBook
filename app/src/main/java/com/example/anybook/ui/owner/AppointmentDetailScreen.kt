package com.example.anybook.ui.owner

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PRIMARY_BLUE = Color(0xFF1565C0)
private val BACKGROUND = Color(0xFFFAFAFA)
private val CARD_BACKGROUND = Color.White
private val TEXT_DARK = Color(0xFF212121)
private val TEXT_LIGHT = Color(0xFF757575)
private val ERROR_RED = Color(0xFFD32F2F)
private val SUCCESS_GREEN = Color(0xFF2E7D32)

// ============ DATA MODEL ============
data class AppointmentDetail(
    val id: String,
    val clientName: String,
    val clientPhone: String,
    val service: String,
    val date: String,
    val time: String,
    val duration: String,
    val price: String,
    val status: String // "Upcoming", "Completed", "Cancelled"
)

data class AppointmentDetailCallbacks(
    val onBackClick: () -> Unit,
    val onMarkCompleted: (String) -> Unit,
    val onCancelAppointment: (String) -> Unit
)

@Composable
fun AppointmentDetailScreen(
    appointment: AppointmentDetail,
    callbacks: AppointmentDetailCallbacks
) {
    val context = LocalContext.current
    var showCancelDialog by remember { mutableStateOf(false) }

    val statusColor = when (appointment.status) {
        "Upcoming" -> PRIMARY_BLUE
        "Completed" -> SUCCESS_GREEN
        else -> ERROR_RED
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BACKGROUND)
    ) {
        // ============ HEADER ============
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CARD_BACKGROUND)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = callbacks.onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = PRIMARY_BLUE
                )
            }
            Text(
                text = "Appointment Details",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TEXT_DARK
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ============ STATUS BADGE ============
            Box(
                modifier = Modifier
                    .background(statusColor.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = appointment.status,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = statusColor
                )
            }

            // ============ CLIENT INFO CARD ============
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CARD_BACKGROUND, RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(PRIMARY_BLUE.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = appointment.clientName.take(1).uppercase(),
                            color = PRIMARY_BLUE,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(appointment.clientName, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TEXT_DARK)
                        Text(appointment.clientPhone, fontSize = 13.sp, color = TEXT_LIGHT)
                    }
                }

                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${appointment.clientPhone}")
                        }
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PRIMARY_BLUE),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Call Client", fontWeight = FontWeight.Medium)
                }
            }

            // ============ APPOINTMENT INFO CARD ============
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CARD_BACKGROUND, RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = appointment.service,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TEXT_DARK
                )

                DetailInfoRow(icon = Icons.Default.CalendarToday, label = "Date", value = appointment.date)
                DetailInfoRow(icon = Icons.Default.Schedule, label = "Time", value = appointment.time)
                DetailInfoRow(icon = Icons.Default.Timer, label = "Duration", value = appointment.duration)
                DetailInfoRow(icon = Icons.Default.CurrencyRupee, label = "Price", value = appointment.price)
            }

            Spacer(modifier = Modifier.weight(1f))

            // ============ ACTION BUTTONS ============
            if (appointment.status == "Upcoming") {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = { callbacks.onMarkCompleted(appointment.id) },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SUCCESS_GREEN),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Mark as Completed", fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    OutlinedButton(
                        onClick = { showCancelDialog = true },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ERROR_RED),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Cancel Appointment", fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }

    // ============ CANCEL CONFIRMATION DIALOG ============
    if (showCancelDialog) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Cancel Appointment?") },
            text = { Text("This will notify ${appointment.clientName} that their appointment has been cancelled. This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    showCancelDialog = false
                    callbacks.onCancelAppointment(appointment.id)
                }) {
                    Text("Yes, Cancel", color = ERROR_RED)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCancelDialog = false }) {
                    Text("Go Back")
                }
            }
        )
    }
}

// ============ REUSABLE COMPONENT ============
@Composable
private fun DetailInfoRow(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = PRIMARY_BLUE, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(label, fontSize = 13.sp, color = TEXT_LIGHT, modifier = Modifier.width(80.dp))
        Text(value, fontSize = 14.sp, color = TEXT_DARK, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true, widthDp = 480, heightDp = 900)
@Composable
fun AppointmentDetailScreenPreview() {
    AppointmentDetailScreen(
        appointment = AppointmentDetail(
            id = "1",
            clientName = "Ananya Sharma",
            clientPhone = "+91 98765 43210",
            service = "Haircut & Style",
            date = "Mon, 30 Jun 2026",
            time = "2:00 PM",
            duration = "45 min",
            price = "₹500",
            status = "Upcoming"
        ),
        callbacks = AppointmentDetailCallbacks(
            onBackClick = {},
            onMarkCompleted = {},
            onCancelAppointment = {}
        )
    )
}