package com.example.anybook.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.material3.Surface
import com.example.anybook.ui.components.AppButton
import com.example.anybook.ui.components.ButtonVariant

private val PRIMARY_BLUE = Color(0xFF1565C0)
private val BACKGROUND = Color(0xFFFAFAFA)
private val CARD_BG = Color.White
private val TEXT_DARK = Color(0xFF212121)
private val TEXT_LIGHT = Color(0xFF757575)
private val ERROR_RED = Color(0xFFD32F2F)

data class Service(val id: String, val name: String, val price: String, val duration: String)

data class BusinessSetupCallbacks(
    val onCompleteSetup: (info: String, services: List<Service>) -> Unit,
    val onBackClick: () -> Unit
)

@Composable
fun BusinessSetupScreen(
    businessId: String,
    callbacks: BusinessSetupCallbacks
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    var businessHours by remember { mutableStateOf("9:00 AM - 6:00 PM") }
    var businessDesc by remember { mutableStateOf("") }

    var servicesList by remember { mutableStateOf(listOf<Service>()) }
    var serviceName by remember { mutableStateOf("") }
    var servicePrice by remember { mutableStateOf("") }
    var serviceDuration by remember { mutableStateOf("") }

    var imageCount by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BACKGROUND)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CARD_BG)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = callbacks.onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = PRIMARY_BLUE)
            }
            Text("Business Setup", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TEXT_DARK, modifier = Modifier.weight(1f))
        }

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = CARD_BG,
            contentColor = PRIMARY_BLUE
        ) {
            Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                Text("Info", modifier = Modifier.padding(12.dp), fontSize = 12.sp)
            }
            Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                Text("Services", modifier = Modifier.padding(12.dp), fontSize = 12.sp)
            }
            Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }) {
                Text("Images", modifier = Modifier.padding(12.dp), fontSize = 12.sp)
            }
            Tab(selected = selectedTab == 3, onClick = { selectedTab = 3 }) {
                Text("Complete", modifier = Modifier.padding(12.dp), fontSize = 12.sp)
            }
        }

        when (selectedTab) {
            0 -> InfoTab(businessHours = businessHours, businessDesc = businessDesc,
                onHoursChange = { businessHours = it }, onDescChange = { businessDesc = it })

            1 -> ServicesTab(
                services = servicesList,
                serviceName = serviceName,
                servicePrice = servicePrice,
                serviceDuration = serviceDuration,
                onServiceNameChange = { serviceName = it },
                onServicePriceChange = { servicePrice = it },
                onServiceDurationChange = { serviceDuration = it },
                onAddService = {
                    if (serviceName.isNotEmpty() && servicePrice.isNotEmpty() && serviceDuration.isNotEmpty()) {
                        servicesList = servicesList + Service(
                            id = System.currentTimeMillis().toString(),
                            name = serviceName,
                            price = servicePrice,
                            duration = serviceDuration
                        )
                        serviceName = ""
                        servicePrice = ""
                        serviceDuration = ""
                    }
                },
                onDeleteService = { serviceId -> servicesList = servicesList.filter { it.id != serviceId } }
            )

            2 -> ImagesTab(imageCount = imageCount, onImageCountChange = { imageCount = it })

            3 -> CompleteTab(businessHours = businessHours, servicesCount = servicesList.size, imagesCount = imageCount)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CARD_BG)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (selectedTab > 0) {
                Button(
                    onClick = { selectedTab-- },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0))
                ) {
                    Text("Back", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TEXT_DARK)
                }
            }

            AppButton(
                text = if (selectedTab == 3) "Complete Setup" else "Next",
                modifier = Modifier.weight(1f),
                onClick = {
                    if (selectedTab < 3) {
                        selectedTab++
                    } else {
                        callbacks.onCompleteSetup(businessHours, servicesList)
                    }
                }
            )
        }
    }
}

@Composable
fun InfoTab(
    businessHours: String,
    businessDesc: String,
    onHoursChange: (String) -> Unit,
    onDescChange: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Business Information", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TEXT_DARK)
        }

        item {
            Column {
                Text("Business Hours", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TEXT_LIGHT, modifier = Modifier.padding(bottom = 8.dp))
                OutlinedTextField(
                    value = businessHours,
                    onValueChange = onHoursChange,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    placeholder = { Text("9:00 AM - 6:00 PM", fontSize = 12.sp) },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PRIMARY_BLUE,
                        unfocusedBorderColor = Color.LightGray
                    )
                )
            }
        }

        item {
            Column {
                Text("Business Description", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TEXT_LIGHT, modifier = Modifier.padding(bottom = 8.dp))
                OutlinedTextField(
                    value = businessDesc,
                    onValueChange = onDescChange,
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    placeholder = { Text("Describe your business...", fontSize = 12.sp) },
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PRIMARY_BLUE,
                        unfocusedBorderColor = Color.LightGray
                    )
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun ServicesTab(
    services: List<Service>,
    serviceName: String,
    servicePrice: String,
    serviceDuration: String,
    onServiceNameChange: (String) -> Unit,
    onServicePriceChange: (String) -> Unit,
    onServiceDurationChange: (String) -> Unit,
    onAddService: () -> Unit,
    onDeleteService: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Manage Services", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TEXT_DARK)
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = serviceName,
                    onValueChange = onServiceNameChange,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    placeholder = { Text("Service name", fontSize = 12.sp) },
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PRIMARY_BLUE, unfocusedBorderColor = Color.LightGray)
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = servicePrice,
                        onValueChange = onServicePriceChange,
                        modifier = Modifier.weight(1f).height(48.dp),
                        placeholder = { Text("Price", fontSize = 12.sp) },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PRIMARY_BLUE, unfocusedBorderColor = Color.LightGray)
                    )

                    OutlinedTextField(
                        value = serviceDuration,
                        onValueChange = onServiceDurationChange,
                        modifier = Modifier.weight(1f).height(48.dp),
                        placeholder = { Text("Duration", fontSize = 12.sp) },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PRIMARY_BLUE, unfocusedBorderColor = Color.LightGray)
                    )
                }

                AppButton(
                    text = "Add Service",
                    icon = Icons.Filled.Add,
                    variant = ButtonVariant.SUCCESS,
                    onClick = onAddService
                )
            }
        }

        items(services) { service ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(service.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TEXT_DARK)
                        Text("${service.price} • ${service.duration}", fontSize = 12.sp, color = TEXT_LIGHT)
                    }
                    IconButton(onClick = { onDeleteService(service.id) }) {
                        Icon(Icons.Filled.Delete, null, tint = ERROR_RED)
                    }
                }
            }
        }
    }
}

@Composable
fun ImagesTab(imageCount: Int, onImageCountChange: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Upload Business Images", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TEXT_DARK)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clickable { onImageCountChange(imageCount + 1) },
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📸", fontSize = 40.sp)
                    Text("Click to upload image", fontSize = 12.sp, color = PRIMARY_BLUE, fontWeight = FontWeight.Medium)
                }
            }
        }

        if (imageCount > 0) {
            Text("Images uploaded: $imageCount", fontSize = 12.sp, color = TEXT_LIGHT)
        }
    }
}

@Composable
fun CompleteTab(businessHours: String, servicesCount: Int, imagesCount: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Review Summary", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TEXT_DARK)

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                SummaryRow("Business Hours", businessHours)
                SummaryRow("Services Added", "$servicesCount service(s)")
                SummaryRow("Images Uploaded", "$imagesCount image(s)")
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(8.dp))
                .padding(12.dp)
        ) {
            Text("✓ All setup complete. Click 'Complete Setup' to finish.", fontSize = 12.sp, color = Color(0xFF2E7D32))
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontSize = 13.sp, color = TEXT_LIGHT)
        Text(value, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = TEXT_DARK)
    }
}


@Preview(showBackground = true, widthDp = 480, heightDp = 600)
@Composable
fun InfoTabPreview() {
    Surface(color = Color.White) {
        InfoTab(
            businessHours = "9:00 AM - 6:00 PM",
            businessDesc = "Professional hair and beauty services for everyone",
            onHoursChange = {},
            onDescChange = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 480, heightDp = 800)
@Composable
fun ServicesTabPreview() {
    Surface(color = Color.White) {
        ServicesTab(
            services = listOf(
                Service("1", "Haircut", "₹500", "1 hour"),
                Service("2", "Hair Coloring", "₹1500", "2 hours"),
                Service("3", "Facial", "₹800", "45 min")
            ),
            serviceName = "",
            servicePrice = "",
            serviceDuration = "",
            onServiceNameChange = {},
            onServicePriceChange = {},
            onServiceDurationChange = {},
            onAddService = {},
            onDeleteService = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 480, heightDp = 600)
@Composable
fun ImagesTabPreview() {
    Surface(color = Color.White) {
        ImagesTab(
            imageCount = 2,
            onImageCountChange = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 480, heightDp = 600)
@Composable
fun CompleteTabPreview() {
    Surface(color = Color.White) {
        CompleteTab(
            businessHours = "9:00 AM - 6:00 PM",
            servicesCount = 3,
            imagesCount = 2
        )
    }
}

@Preview(showBackground = true, widthDp = 480, heightDp = 900)
@Composable
fun BusinessSetupScreenFullPreview() {
    BusinessSetupScreen(
        businessId = "biz_001",
        callbacks = BusinessSetupCallbacks(
            onCompleteSetup = { info, services -> println("Setup complete") },
            onBackClick = {}
        )
    )
}