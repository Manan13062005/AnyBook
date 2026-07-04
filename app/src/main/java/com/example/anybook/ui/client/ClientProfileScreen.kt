package com.example.anybook.ui.client

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
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
import androidx.compose.foundation.clickable

private val PRIMARY_BLUE = Color(0xFF1565C0)
private val BACKGROUND = Color(0xFFFAFAFA)
private val CARD_BACKGROUND = Color.White
private val TEXT_DARK = Color(0xFF212121)
private val TEXT_LIGHT = Color(0xFF757575)
private val ERROR_RED = Color(0xFFD32F2F)

data class ClientProfileCallbacks(
    val onBackClick: () -> Unit,
    val onMyBookingsClick: () -> Unit,
    val onSwitchToOwner: () -> Unit,
    val onLogout: () -> Unit
)

@Composable
fun ClientProfileScreen(
    name: String,
    email: String,
    phone: String,
    callbacks: ClientProfileCallbacks
) {
    var showSwitchRoleDialog by remember { mutableStateOf(false) }

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
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = PRIMARY_BLUE)
            }
            Text("Profile", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TEXT_DARK)
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // ============ PROFILE INFO CARD ============
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CARD_BACKGROUND, RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(PRIMARY_BLUE.copy(alpha = 0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(name.take(1).uppercase(), color = PRIMARY_BLUE, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TEXT_DARK)
                    Text(email, fontSize = 13.sp, color = TEXT_LIGHT)
                    Text(phone, fontSize = 13.sp, color = TEXT_LIGHT)
                }
            }

            // ============ MENU OPTIONS ============
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CARD_BACKGROUND, RoundedCornerShape(12.dp))
            ) {
                ProfileMenuItem(
                    icon = Icons.Default.CalendarToday,
                    label = "My Bookings",
                    onClick = callbacks.onMyBookingsClick
                )
                Divider(color = Color(0xFFEEEEEE))
                ProfileMenuItem(
                    icon = Icons.Default.SwapHoriz,
                    label = "Switch to Owner Account",
                    onClick = { showSwitchRoleDialog = true }
                )
            }

            // ============ LOGOUT ============
            Button(
                onClick = callbacks.onLogout,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ERROR_RED),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Log Out", fontWeight = FontWeight.Bold)
            }
        }
    }

    // ============ SWITCH ROLE CONFIRMATION DIALOG ============
    if (showSwitchRoleDialog) {
        AlertDialog(
            onDismissRequest = { showSwitchRoleDialog = false },
            title = { Text("Switch to Owner Account?") },
            text = { Text("You're currently logged in as a Client. To manage a business, you'll need to log in with a separate Owner account.") },
            confirmButton = {
                TextButton(onClick = {
                    showSwitchRoleDialog = false
                    callbacks.onSwitchToOwner()
                }) { Text("Continue", color = PRIMARY_BLUE) }
            },
            dismissButton = {
                TextButton(onClick = { showSwitchRoleDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
private fun ProfileMenuItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = PRIMARY_BLUE)
        Spacer(modifier = Modifier.width(12.dp))
        Text(label, fontSize = 14.sp, color = TEXT_DARK, modifier = Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TEXT_LIGHT)
    }
}

@Preview(showBackground = true, widthDp = 480, heightDp = 900)
@Composable
fun ClientProfileScreenPreview() {
    ClientProfileScreen(
        name = "Ananya Sharma",
        email = "ananya.sharma@email.com",
        phone = "+91 98765 43210",
        callbacks = ClientProfileCallbacks(
            onBackClick = {},
            onMyBookingsClick = {},
            onSwitchToOwner = {},
            onLogout = {}
        )
    )
}