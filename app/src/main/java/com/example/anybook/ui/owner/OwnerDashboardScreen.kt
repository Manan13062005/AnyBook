package com.example.anybook.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.automirrored.filled.Logout
import com.example.anybook.ui.components.AppButton
import com.example.anybook.ui.components.AppointmentCard
import com.example.anybook.ui.components.ButtonVariant
import com.example.anybook.ui.components.EmptyState

private val PRIMARY_BLUE = Color(0xFF1565C0)
private val BACKGROUND = Color(0xFFFAFAFA)
private val CARD_BACKGROUND = Color.White
private val TEXT_DARK = Color(0xFF212121)
private val TEXT_LIGHT = Color(0xFF757575)

data class BusinessInfo(
    val businessName: String,
    val category: String,
    val address: String
)

data class OwnerBookingSummary(
    val id: String,
    val clientName: String,
    val service: String,
    val time: String,
    val status: String // "Upcoming", "Completed"
)

data class OwnerDashboardCallbacks(
    val onEditBusiness: () -> Unit,
    val onViewAllBookings: () -> Unit,
    val onBookingClick: (String) -> Unit,
    val onSwitchToClient: () -> Unit,
    val onLogout: () -> Unit
)

@Composable
fun OwnerDashboardScreen(
    businessInfo: BusinessInfo,
    bookings: List<OwnerBookingSummary>,
    todayCount: Int,
    totalCount: Int,
    weekCount: Int,
    callbacks: OwnerDashboardCallbacks
) {
    var showAccountDialog by remember { mutableStateOf(false) }
    var showSwitchRoleDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BACKGROUND)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CARD_BACKGROUND)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dashboard",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TEXT_DARK,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { showAccountDialog = true }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Account",
                    tint = PRIMARY_BLUE,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(label = "Today", value = todayCount.toString(), modifier = Modifier.weight(1f))
                StatCard(label = "This Week", value = weekCount.toString(), modifier = Modifier.weight(1f))
                StatCard(label = "Total", value = totalCount.toString(), modifier = Modifier.weight(1f))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CARD_BACKGROUND, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(businessInfo.businessName, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TEXT_DARK)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(businessInfo.category, fontSize = 13.sp, color = PRIMARY_BLUE)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(businessInfo.address, fontSize = 12.sp, color = TEXT_LIGHT)
                    }
                    TextButton(onClick = callbacks.onEditBusiness) {
                        Text("Edit", color = PRIMARY_BLUE, fontWeight = FontWeight.Medium)
                    }
                }
            }

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Upcoming Bookings",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TEXT_DARK,
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = callbacks.onViewAllBookings) {
                        Text("View All", color = PRIMARY_BLUE, fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (bookings.isEmpty()) {
                    EmptyState(
                        icon = Icons.Default.EventBusy,
                        title = "No upcoming bookings yet",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(CARD_BACKGROUND, RoundedCornerShape(12.dp))
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        bookings.forEach { booking ->
                            AppointmentCard(
                                clientName = booking.clientName,
                                service = booking.service,
                                time = booking.time,
                                status = booking.status,
                                onClick = { callbacks.onBookingClick(booking.id) }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showAccountDialog) {
        AlertDialog(
            onDismissRequest = { showAccountDialog = false },
            title = { Text("Account") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    AppButton(
                        text = "Switch to Client Account",
                        icon = Icons.Default.SwapHoriz,
                        variant = ButtonVariant.OUTLINED,
                        onClick = {
                            showAccountDialog = false
                            showSwitchRoleDialog = true
                        }
                    )
                    AppButton(
                        text = "Log Out",
                        icon = Icons.AutoMirrored.Filled.Logout,
                        variant = ButtonVariant.DESTRUCTIVE,
                        onClick = {
                            showAccountDialog = false
                            callbacks.onLogout()
                        }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showAccountDialog = false }) { Text("Close") }
            }
        )
    }

    if (showSwitchRoleDialog) {
        AlertDialog(
            onDismissRequest = { showSwitchRoleDialog = false },
            title = { Text("Switch to Client Account?") },
            text = { Text("You're currently logged in as an Owner. To book services, you'll need to log in with a separate Client account.") },
            confirmButton = {
                TextButton(onClick = {
                    showSwitchRoleDialog = false
                    callbacks.onSwitchToClient()
                }) { Text("Continue", color = PRIMARY_BLUE) }
            },
            dismissButton = {
                TextButton(onClick = { showSwitchRoleDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(CARD_BACKGROUND, RoundedCornerShape(12.dp))
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PRIMARY_BLUE)
            Spacer(modifier = Modifier.height(4.dp))
            Text(label, fontSize = 12.sp, color = TEXT_LIGHT)
        }
    }
}

@Preview(showBackground = true, widthDp = 480, heightDp = 900)
@Composable
fun OwnerDashboardScreenPreview() {
    OwnerDashboardScreen(
        businessInfo = BusinessInfo("Priya Beauty Studio", "Hair & Beauty", "Connaught Place, New Delhi"),
        bookings = listOf(
            OwnerBookingSummary("1", "Ananya Sharma", "Haircut & Style", "2:00 PM", "Upcoming"),
            OwnerBookingSummary("2", "Rohit Verma", "Beard Trim", "4:30 PM", "Upcoming"),
            OwnerBookingSummary("3", "Kavita Singh", "Facial", "Yesterday", "Completed")
        ),
        todayCount = 5,
        totalCount = 128,
        weekCount = 22,
        callbacks = OwnerDashboardCallbacks(
            onEditBusiness = {},
            onViewAllBookings = {},
            onBookingClick = {},
            onSwitchToClient = {},
            onLogout = {}
        )
    )
}