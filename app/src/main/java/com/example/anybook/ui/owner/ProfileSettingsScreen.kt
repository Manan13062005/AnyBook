package com.example.anybook.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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

private val PRIMARY_BLUE = Color(0xFF1565C0)
private val LIGHT_BLUE = Color(0xFF64B5F6)
private val BACKGROUND = Color(0xFFFAFAFA)
private val CARD_BACKGROUND = Color.White
private val TEXT_DARK = Color(0xFF212121)
private val TEXT_LIGHT = Color(0xFF757575)
private val ERROR_RED = Color(0xFFD32F2F)

data class ProfileSettingsCallbacks(
    val onCompleteSetup: (businessName: String, address: String, phone: String, category: String) -> Unit,
    val onBackClick: () -> Unit
)

@Composable
fun ProfileSettingsScreen(
    callbacks: ProfileSettingsCallbacks
) {
    var businessName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    var businessNameError by remember { mutableStateOf("") }
    var addressError by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf("") }
    var categoryError by remember { mutableStateOf("") }

    val isFormValid = businessName.isNotEmpty() && address.isNotEmpty() &&
            phone.isNotEmpty() && category.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BACKGROUND)
    ) {
        // Header
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
                text = "Business Setup",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TEXT_DARK,
                modifier = Modifier.weight(1f)
            )
        }

        // Form Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Title Section
            Column {
                Text(
                    text = "Complete Your Business Profile",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TEXT_DARK
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Fill in your business details to get started",
                    fontSize = 14.sp,
                    color = TEXT_LIGHT
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Business Name Field
            Column {
                Text(
                    text = "Business Name *",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = TEXT_DARK,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = businessName,
                    onValueChange = {
                        businessName = it
                        businessNameError = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    placeholder = { Text("e.g., Priya Beauty Studio", fontSize = 13.sp, color = TEXT_LIGHT) },
                    singleLine = true,
                    isError = businessNameError.isNotEmpty(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PRIMARY_BLUE,
                        unfocusedBorderColor = Color.LightGray,
                        errorBorderColor = ERROR_RED,
                        focusedTextColor = TEXT_DARK,
                        unfocusedTextColor = TEXT_DARK
                    )
                )
                if (businessNameError.isNotEmpty()) {
                    Text(
                        text = businessNameError,
                        fontSize = 12.sp,
                        color = ERROR_RED,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Address Field
            Column {
                Text(
                    text = "Business Address *",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = TEXT_DARK,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = address,
                    onValueChange = {
                        address = it
                        addressError = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    placeholder = { Text("e.g., Connaught Place, New Delhi", fontSize = 13.sp, color = TEXT_LIGHT) },
                    singleLine = true,
                    isError = addressError.isNotEmpty(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PRIMARY_BLUE,
                        unfocusedBorderColor = Color.LightGray,
                        errorBorderColor = ERROR_RED,
                        focusedTextColor = TEXT_DARK,
                        unfocusedTextColor = TEXT_DARK
                    )
                )
                if (addressError.isNotEmpty()) {
                    Text(
                        text = addressError,
                        fontSize = 12.sp,
                        color = ERROR_RED,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Phone Number Field
            Column {
                Text(
                    text = "Phone Number *",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = TEXT_DARK,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        phone = it
                        phoneError = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    placeholder = { Text("+91 98765 43210", fontSize = 13.sp, color = TEXT_LIGHT) },
                    singleLine = true,
                    isError = phoneError.isNotEmpty(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PRIMARY_BLUE,
                        unfocusedBorderColor = Color.LightGray,
                        errorBorderColor = ERROR_RED,
                        focusedTextColor = TEXT_DARK,
                        unfocusedTextColor = TEXT_DARK
                    )
                )
                if (phoneError.isNotEmpty()) {
                    Text(
                        text = phoneError,
                        fontSize = 12.sp,
                        color = ERROR_RED,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Category Field
            Column {
                Text(
                    text = "Business Category *",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = TEXT_DARK,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = category,
                    onValueChange = {
                        category = it
                        categoryError = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    placeholder = { Text("e.g., Hair & Beauty, Gym, Health, Photography", fontSize = 12.sp, color = TEXT_LIGHT) },
                    singleLine = true,
                    isError = categoryError.isNotEmpty(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PRIMARY_BLUE,
                        unfocusedBorderColor = Color.LightGray,
                        errorBorderColor = ERROR_RED,
                        focusedTextColor = TEXT_DARK,
                        unfocusedTextColor = TEXT_DARK
                    )
                )
                if (categoryError.isNotEmpty()) {
                    Text(
                        text = categoryError,
                        fontSize = 12.sp,
                        color = ERROR_RED,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Info Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = "💡 All fields are required to complete your business setup",
                    fontSize = 12.sp,
                    color = PRIMARY_BLUE
                )
            }
        }

        // Bottom Button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(CARD_BACKGROUND)
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    // Validate form
                    var isValid = true

                    if (businessName.isEmpty()) {
                        businessNameError = "Business name is required"
                        isValid = false
                    }
                    if (address.isEmpty()) {
                        addressError = "Address is required"
                        isValid = false
                    }
                    if (phone.isEmpty()) {
                        phoneError = "Phone number is required"
                        isValid = false
                    }
                    if (category.isEmpty()) {
                        categoryError = "Category is required"
                        isValid = false
                    }

                    if (isValid) {
                        callbacks.onCompleteSetup(businessName, address, phone, category)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PRIMARY_BLUE),
                enabled = isFormValid,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Complete Setup",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 480, heightDp = 900)
@Composable
fun ProfileSettingsScreenPreview() {
    ProfileSettingsScreen(
        callbacks = ProfileSettingsCallbacks(
            onCompleteSetup = { name, address, phone, category ->
                println("Setup: $name, $address, $phone, $category")
            },
            onBackClick = {}
        )
    )
}

