package com.example.anybook.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PRIMARY_BLUE = Color(0xFF1565C0)
private val SUCCESS_GREEN = Color(0xFF2E7D32)
private val ERROR_RED = Color(0xFFD32F2F)

@Composable
fun StatusChip(
    status: String,
    modifier: Modifier = Modifier
) {
    val statusColor = statusToColor(status)

    Box(
        modifier = modifier
            .background(statusColor.copy(alpha = 0.1f), RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = status,
            fontSize = 10.sp,
            color = statusColor,
            fontWeight = FontWeight.Medium
        )
    }
}

fun statusToColor(status: String): Color {
    return when (status) {
        "Upcoming" -> PRIMARY_BLUE
        "Completed" -> SUCCESS_GREEN
        "Cancelled" -> ERROR_RED
        else -> ERROR_RED
    }
}

@Preview(showBackground = true, widthDp = 200)
@Composable
fun StatusChipPreview_Upcoming() {
    StatusChip(status = "Upcoming")
}

@Preview(showBackground = true, widthDp = 200)
@Composable
fun StatusChipPreview_Completed() {
    StatusChip(status = "Completed")
}

@Preview(showBackground = true, widthDp = 200)
@Composable
fun StatusChipPreview_Cancelled() {
    StatusChip(status = "Cancelled")
}