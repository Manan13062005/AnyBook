package com.example.anybook.ui.client

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.rememberDatePickerState
import com.example.anybook.ui.components.AppButton

private val PRIMARY_BLUE = Color(0xFF1565C0)
private val BACKGROUND = Color(0xFFFAFAFA)
private val CARD_BACKGROUND = Color.White
private val TEXT_DARK = Color(0xFF212121)
private val TEXT_LIGHT = Color(0xFF757575)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessDetailScreen(
    businessId: String,
    onBack: () -> Unit,
    onNext: (serviceId: String, dateTime: String) -> Unit
) {
    val business = getMockBusinesses().find { it.id == businessId } ?: getMockBusinesses().first()
    val services = getServices(businessId)
    val slots = listOf("09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "02:00 PM", "02:30 PM", "03:00 PM", "03:30 PM", "04:00 PM", "04:30 PM")

    var selectedService by remember { mutableStateOf(services.first().first) }
    var selectedDate by remember { mutableStateOf("Today") }
    var selectedTime by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(business.name, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TEXT_DARK) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = PRIMARY_BLUE) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CARD_BACKGROUND)
            )
        },
        containerColor = BACKGROUND
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            LazyColumn(Modifier.fillMaxWidth().padding(bottom = 70.dp)) {
                item {
                    Column(Modifier.fillMaxWidth().background(CARD_BACKGROUND).padding(16.dp)) {
                        Row(Modifier.fillMaxWidth().padding(bottom = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(business.category, fontSize = 14.sp, color = TEXT_LIGHT)
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(Icons.Default.Star, null, tint = Color(0xFFFFB300), modifier = Modifier.size(16.dp))
                                Text("${String.format("%.1f", business.rating)} (${business.reviewCount})", fontSize = 12.sp, color = TEXT_DARK)
                            }
                        }
                        Divider(thickness = 0.5.dp, modifier = Modifier.padding(vertical = 12.dp))
                        Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.LocationOn, null, tint = PRIMARY_BLUE, modifier = Modifier.size(18.dp))
                            Text(business.address, fontSize = 13.sp, color = TEXT_DARK)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                            Icon(Icons.Default.Phone, null, tint = PRIMARY_BLUE, modifier = Modifier.size(18.dp))
                            Text("09876543210", fontSize = 13.sp, color = TEXT_DARK)
                        }
                    }
                }

                item {
                    Column(Modifier.fillMaxWidth().padding(16.dp)) {
                        Text("Select Service", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TEXT_DARK, modifier = Modifier.padding(bottom = 10.dp))
                        services.forEach { (id, name, price) ->
                            Card(
                                Modifier.fillMaxWidth().clickable { selectedService = id }.padding(bottom = 8.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = if (selectedService == id) Color(0xFFE3F2FD) else CARD_BACKGROUND),
                                border = if (selectedService == id) BorderStroke(2.dp, PRIMARY_BLUE) else BorderStroke(0.5.dp, Color.LightGray)
                            ) {
                                Row(Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(name, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TEXT_DARK)
                                    Text(price, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PRIMARY_BLUE)
                                }
                            }
                        }
                    }
                }

                item {
                    Column(Modifier.fillMaxWidth().padding(16.dp)) {
                        Text("Select Date", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TEXT_DARK, modifier = Modifier.padding(bottom = 10.dp))

                        var showDatePicker by remember { mutableStateOf(false) }
                        var dateDisplay by remember { mutableStateOf("Today") }

                        OutlinedButton(
                            onClick = { showDatePicker = true },
                            Modifier.fillMaxWidth().height(40.dp),
                            border = BorderStroke(1.dp, PRIMARY_BLUE)
                        ) {
                            Icon(Icons.Default.DateRange, null, tint = PRIMARY_BLUE, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(dateDisplay, fontSize = 13.sp, color = TEXT_DARK)
                        }

                        if (showDatePicker) {
                            val datePickerState = rememberDatePickerState()
                            DatePickerDialog(
                                onDismissRequest = { showDatePicker = false },
                                confirmButton = {
                                    Button(onClick = {
                                        datePickerState.selectedDateMillis?.let {
                                            dateDisplay = java.text.SimpleDateFormat("dd MMM yyyy").format(it)
                                            selectedDate = dateDisplay
                                        }
                                        showDatePicker = false
                                    }) {
                                        Text("OK")
                                    }
                                }
                            ) {
                                DatePicker(state = datePickerState)
                            }
                        }
                    }
                }

                item {
                    Column(Modifier.fillMaxWidth().padding(16.dp)) {
                        Text("Select Time", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TEXT_DARK, modifier = Modifier.padding(bottom = 10.dp))
                        slots.chunked(3).forEach { row ->
                            Row(Modifier.fillMaxWidth().padding(bottom = 6.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                row.forEach { slot ->
                                    Button(
                                        onClick = { selectedTime = slot },
                                        Modifier.weight(1f).height(36.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = if (selectedTime == slot) PRIMARY_BLUE else CARD_BACKGROUND),
                                        border = BorderStroke(0.5.dp, if (selectedTime == slot) PRIMARY_BLUE else Color.LightGray),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(slot, fontSize = 10.sp, color = if (selectedTime == slot) Color.White else TEXT_DARK)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            AppButton(
                text = "Continue",
                enabled = selectedTime.isNotEmpty(),
                modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp),
                onClick = { if (selectedTime.isNotEmpty()) onNext(selectedService, "$selectedDate at $selectedTime") }
            )
        }
    }
}

fun getServices(businessId: String): List<Triple<String, String, String>> = when (businessId) {
    "biz_001" -> listOf(Triple("srv_1", "Haircut", "₹500"), Triple("srv_2", "Coloring", "₹1500"), Triple("srv_3", "Facial", "₹800"))
    "biz_002" -> listOf(Triple("srv_4", "Personal Training", "₹1000"), Triple("srv_5", "Yoga", "₹300"), Triple("srv_6", "Membership", "₹2500"))
    "biz_003" -> listOf(Triple("srv_7", "Checkup", "₹500"), Triple("srv_8", "Vaccination", "₹200"), Triple("srv_9", "Therapy", "₹1200"))
    else -> listOf(Triple("srv_d1", "Service 1", "₹500"), Triple("srv_d2", "Service 2", "₹1000"))
}

@Preview(showBackground = true, widthDp = 480, heightDp = 900)
@Composable
fun BusinessDetailScreenPreview() {
    BusinessDetailScreen(
        businessId = "biz_001",
        onBack = { println("Back clicked") },
        onNext = { serviceId, dateTime ->
            println("Next: $serviceId at $dateTime")
        }
    )
}
