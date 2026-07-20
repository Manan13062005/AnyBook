package com.example.anybook.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anybook.ui.components.AppointmentCard
import com.example.anybook.ui.components.EmptyState

private val PRIMARY_BLUE = Color(0xFF1565C0)
private val BACKGROUND = Color(0xFFFAFAFA)
private val CARD_BACKGROUND = Color.White
private val TEXT_DARK = Color(0xFF212121)

enum class AppointmentFilter(val label: String) {
    ALL("All"),
    UPCOMING("Upcoming"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled")
}

data class AppointmentsScreenCallbacks(
    val onBackClick: () -> Unit,
    val onAppointmentClick: (String) -> Unit
)

@Composable
fun AppointmentsScreen(
    appointments: List<OwnerBookingSummary>,
    callbacks: AppointmentsScreenCallbacks
) {
    var selectedFilter by remember { mutableStateOf(AppointmentFilter.ALL) }

    val filteredAppointments = when (selectedFilter) {
        AppointmentFilter.ALL -> appointments
        AppointmentFilter.UPCOMING -> appointments.filter { it.status == "Upcoming" }
        AppointmentFilter.COMPLETED -> appointments.filter { it.status == "Completed" }
        AppointmentFilter.CANCELLED -> appointments.filter { it.status == "Cancelled" }
    }

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
            IconButton(onClick = callbacks.onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = PRIMARY_BLUE
                )
            }
            Text(
                text = "Appointments",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TEXT_DARK
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CARD_BACKGROUND)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AppointmentFilter.entries.forEach { filter ->
                val count = when (filter) {
                    AppointmentFilter.ALL -> appointments.size
                    AppointmentFilter.UPCOMING -> appointments.count { it.status == "Upcoming" }
                    AppointmentFilter.COMPLETED -> appointments.count { it.status == "Completed" }
                    AppointmentFilter.CANCELLED -> appointments.count { it.status == "Cancelled" }
                }
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = { Text("${filter.label} ($count)", fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PRIMARY_BLUE,
                        selectedLabelColor = Color.White,
                        containerColor = BACKGROUND,
                        labelColor = TEXT_DARK
                    )
                )
            }
        }

        if (filteredAppointments.isEmpty()) {
            EmptyState(
                icon = Icons.Default.EventBusy,
                title = "No ${selectedFilter.label.lowercase()} appointments",
                modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredAppointments) { appointment ->
                    AppointmentCard(
                        clientName = appointment.clientName,
                        service = appointment.service,
                        time = appointment.time,
                        status = appointment.status,
                        onClick = { callbacks.onAppointmentClick(appointment.id) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 480, heightDp = 900)
@Composable
fun AppointmentsScreenPreview() {
    AppointmentsScreen(
        appointments = listOf(
            OwnerBookingSummary("1", "Ananya Sharma", "Haircut & Style", "2:00 PM", "Upcoming"),
            OwnerBookingSummary("2", "Rohit Verma", "Beard Trim", "4:30 PM", "Upcoming"),
            OwnerBookingSummary("3", "Kavita Singh", "Facial", "Yesterday", "Completed"),
            OwnerBookingSummary("4", "Amit Kumar", "Hair Color", "2 days ago", "Completed"),
            OwnerBookingSummary("5", "Sneha Reddy", "Manicure", "Last week", "Cancelled")
        ),
        callbacks = AppointmentsScreenCallbacks(
            onBackClick = {},
            onAppointmentClick = {}
        )
    )
}