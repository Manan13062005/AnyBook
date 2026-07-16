package com.example.anybook.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

// ============ BRAND COLORS ============
private val PRIMARY_BLUE = Color(0xFF1565C0)
private val CARD_BACKGROUND = Color.White
private val TEXT_DARK = Color(0xFF212121)
private val TEXT_LIGHT = Color(0xFF757575)
private val ERROR_RED = Color(0xFFD32F2F)
private val SUCCESS_GREEN = Color(0xFF2E7D32)

@Composable
fun AppointmentCard(
    clientName: String,
    service: String,
    time: String,
    status: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}

) {
    val statusColor = when (status) {
        "Upcoming" -> PRIMARY_BLUE
        "Completed" -> SUCCESS_GREEN
        else -> ERROR_RED
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(CARD_BACKGROUND, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(PRIMARY_BLUE.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = clientName.take(1).uppercase(),
                color = PRIMARY_BLUE,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(clientName, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TEXT_DARK)
            Text(service, fontSize = 12.sp, color = TEXT_LIGHT)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(time, fontSize = 12.sp, color = TEXT_DARK)

            Spacer(modifier = Modifier.height(4.dp))

            Box(
                modifier = Modifier
                    .background(statusColor.copy(alpha = 0.1f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(status, fontSize = 10.sp, color = statusColor, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun AppointmentCardPreview_Upcoming() {
    AppointmentCard(
        clientName = "Ananya Sharma",
        service = "Haircut & Style",
        time = "2:00 PM",
        status = "Upcoming",
        onClick = {}
    )
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun AppointmentCardPreview_Completed() {
    AppointmentCard(
        clientName = "Kavita Singh",
        service = "Facial",
        time = "Yesterday",
        status = "Completed",
        onClick = {}
    )
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun AppointmentCardPreview_Cancelled() {
    AppointmentCard(
        clientName = "Sneha Reddy",
        service = "Manicure",
        time = "Last week",
        status = "Cancelled",
        onClick = {}
    )
}
