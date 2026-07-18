package com.example.anybook.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anybook.ui.components.AppButton
import com.example.anybook.ui.components.EmptyState

private val PRIMARY_BLUE = Color(0xFF1565C0)
private val BACKGROUND = Color(0xFFFAFAFA)
private val CARD_BACKGROUND = Color.White
private val TEXT_DARK = Color(0xFF212121)
private val TEXT_LIGHT = Color(0xFF757575)
private val ERROR_RED = Color(0xFFD32F2F)

data class ManagedService(
    val id: String,
    val name: String,
    val price: String,
    val duration: String,
    val isActive: Boolean
)

data class ServicesScreenCallbacks(
    val onBackClick: () -> Unit,
    val onAddService: (name: String, price: String, duration: String) -> Unit,
    val onEditService: (id: String, name: String, price: String, duration: String) -> Unit,
    val onDeleteService: (id: String) -> Unit,
    val onToggleActive: (id: String, isActive: Boolean) -> Unit
)

@Composable
fun ServicesScreen(
    services: List<ManagedService>,
    callbacks: ServicesScreenCallbacks
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var editingService by remember { mutableStateOf<ManagedService?>(null) }
    var deletingService by remember { mutableStateOf<ManagedService?>(null) }

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
                text = "Manage Services",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TEXT_DARK,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                editingService = null
                showBottomSheet = true
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Service",
                    tint = PRIMARY_BLUE
                )
            }
        }

        if (services.isEmpty()) {
            EmptyState(
                icon = Icons.Default.Spa,
                title = "No services added yet",
                subtitle = "ap + to add your first service",
                modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(services) { service ->
                    ServiceCard(
                        service = service,
                        onEditClick = {
                            editingService = service
                            showBottomSheet = true
                        },
                        onDeleteClick = { deletingService = service },
                        onToggleActive = { isActive ->
                            callbacks.onToggleActive(service.id, isActive)
                        }
                    )
                }
            }
        }
    }

    if (showBottomSheet) {
        ServiceFormBottomSheet(
            editingService = editingService,
            onDismiss = { showBottomSheet = false },
            onSave = { name, price, duration ->
                if (editingService != null) {
                    callbacks.onEditService(editingService!!.id, name, price, duration)
                } else {
                    callbacks.onAddService(name, price, duration)
                }
                showBottomSheet = false
            }
        )
    }

    deletingService?.let { service ->
        AlertDialog(
            onDismissRequest = { deletingService = null },
            title = { Text("Delete Service?") },
            text = { Text("\"${service.name}\" will be removed and clients won't be able to book it anymore.") },
            confirmButton = {
                TextButton(onClick = {
                    callbacks.onDeleteService(service.id)
                    deletingService = null
                }) {
                    Text("Delete", color = ERROR_RED)
                }
            },
            dismissButton = {
                TextButton(onClick = { deletingService = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun ServiceCard(
    service: ManagedService,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onToggleActive: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CARD_BACKGROUND, RoundedCornerShape(12.dp))
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = service.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (service.isActive) TEXT_DARK else TEXT_LIGHT
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${formatPrice(service.price)} · ${service.duration}",
                    fontSize = 13.sp,
                    color = if (service.isActive) PRIMARY_BLUE else TEXT_LIGHT
                )
            }
            Switch(
                checked = service.isActive,
                onCheckedChange = onToggleActive,
                colors = SwitchDefaults.colors(checkedTrackColor = PRIMARY_BLUE)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = onEditClick, modifier = Modifier.size(36.dp)) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = TEXT_LIGHT, modifier = Modifier.size(20.dp))
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = onDeleteClick, modifier = Modifier.size(36.dp)) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ERROR_RED, modifier = Modifier.size(20.dp))
            }
        }
    }
}

private fun formatPrice(price: String): String {
    return if (price.isBlank() || price == "0") "Price on request" else "₹$price"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ServiceFormBottomSheet(
    editingService: ManagedService?,
    onDismiss: () -> Unit,
    onSave: (name: String, price: String, duration: String) -> Unit
) {
    var name by remember { mutableStateOf(editingService?.name ?: "") }
    var price by remember { mutableStateOf(editingService?.price ?: "") }
    var duration by remember { mutableStateOf(editingService?.duration ?: "") }

    val sheetState = rememberModalBottomSheetState()
    val isFormValid = name.isNotBlank() && price.isNotBlank() && duration.isNotBlank()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (editingService != null) "Edit Service" else "Add New Service",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TEXT_DARK
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Service Name") },
                placeholder = { Text("e.g., Haircut & Style") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PRIMARY_BLUE)
            )

            OutlinedTextField(
                value = price,
                onValueChange = { price = it.filter { c -> c.isDigit() } },
                label = { Text("Price (₹)") },
                placeholder = { Text("e.g., 500") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PRIMARY_BLUE)
            )

            OutlinedTextField(
                value = duration,
                onValueChange = { duration = it },
                label = { Text("Duration") },
                placeholder = { Text("e.g., 45 min") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PRIMARY_BLUE)
            )

            AppButton(
                text = if(editingService != null) "Save Changes" else " Add Service",
                enabled = isFormValid,
                onClick = {onSave(name, price, duration)}
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 480, heightDp = 900)
@Composable
fun ServicesScreenPreview() {
    ServicesScreen(
        services = listOf(
            ManagedService("1", "Haircut & Style", "500", "45 min", true),
            ManagedService("2", "Beard Trim", "200", "20 min", true),
            ManagedService("3", "Hair Color", "1200", "90 min", false),
            ManagedService("4", "Facial", "800", "60 min", true)
        ),
        callbacks = ServicesScreenCallbacks(
            onBackClick = {},
            onAddService = { _, _, _ -> },
            onEditService = { _, _, _, _ -> },
            onDeleteService = {},
            onToggleActive = { _, _ -> }
        )
    )
}