package com.example.anybook.ui.client

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anybook.data.model.Business
import androidx.compose.ui.tooling.preview.Preview

// Color constants
private val PRIMARY_BLUE = Color(0xFF1565C0)
private val LIGHT_BLUE = Color(0xFF64B5F6)
private val BACKGROUND = Color(0xFFFAFAFA)
private val CARD_BACKGROUND = Color.White
private val TEXT_DARK = Color(0xFF212121)
private val TEXT_LIGHT = Color(0xFF757575)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onBusinessTap: (businessId: String) -> Unit,
    onMyBookingsTap: () -> Unit
) {
    // STATE
    var searchQuery by remember { mutableStateOf("") }
    val mockBusinesses = remember { getMockBusinesses() }

    // SEARCH LOGIC
    val filteredBusinesses = if (searchQuery.isEmpty()) {
        mockBusinesses
    } else {
        mockBusinesses.filter { business ->
            business.name.contains(searchQuery, ignoreCase = true) ||
                    business.category.contains(searchQuery, ignoreCase = true)
        }
    }

    // LAYOUT
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "AnyBook",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = TEXT_DARK
                    )
                },
                actions = {
                    IconButton(onClick = onMyBookingsTap) {
                        Icon(
                            Icons.Default.BookmarkBorder,
                            contentDescription = "My Bookings",
                            tint = PRIMARY_BLUE
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CARD_BACKGROUND,
                    scrolledContainerColor = CARD_BACKGROUND
                )
            )
        },
        containerColor = BACKGROUND
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // SEARCH BAR
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search businesses...") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = TEXT_LIGHT
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PRIMARY_BLUE,
                    unfocusedBorderColor = Color.LightGray,
                    focusedTextColor = TEXT_DARK,
                    unfocusedTextColor = TEXT_DARK
                )
            )

            // BUSINESS LIST
            if (filteredBusinesses.isEmpty()) {
                // Empty state
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No businesses found",
                        color = TEXT_LIGHT,
                        fontSize = 16.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(filteredBusinesses) { business ->
                        BusinessCard(
                            business = business,
                            onTap = { onBusinessTap(business.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BusinessCard(
    business: Business,
    onTap: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTap() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CARD_BACKGROUND),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Name and Rating
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    business.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TEXT_DARK,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFB300),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        String.format("%.1f", business.rating),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = TEXT_DARK
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Category
            Text(
                business.category,
                fontSize = 13.sp,
                color = TEXT_LIGHT
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Featured service / description
            Text(
                "Professional services tailored for you",
                fontSize = 13.sp,
                color = TEXT_LIGHT,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(12.dp))

            // View Details Button
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "View Details",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = LIGHT_BLUE
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "→",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = LIGHT_BLUE
                )
            }
        }
    }
}

// MOCK DATA
fun getMockBusinesses(): List<Business> {
    return listOf(
        Business(
            id = "biz_001",
            ownerId = "owner_001",
            name = "Priya Beauty Studio",
            category = "Hair & Beauty",
            address = "Connaught Place, New Delhi",
            rating = 4.8f,
            reviewCount = 125
        ),
        Business(
            id = "biz_002",
            ownerId = "owner_002",
            name = "FitZone Gym",
            category = "Gym & Training",
            address = "Karol Bagh, Delhi",
            rating = 4.6f,
            reviewCount = 98
        ),
        Business(
            id = "biz_003",
            ownerId = "owner_003",
            name = "Dr. Healing Wellness Clinic",
            category = "Health & Wellness",
            address = "Rajouri Garden, Delhi",
            rating = 4.9f,
            reviewCount = 156
        ),
        Business(
            id = "biz_004",
            ownerId = "owner_004",
            name = "ShutterClick Photography",
            category = "Photography & Art",
            address = "Dwarka, Delhi",
            rating = 4.7f,
            reviewCount = 87
        ),
        Business(
            id = "biz_005",
            ownerId = "owner_005",
            name = "Spice & Flavor Catering",
            category = "Food & Catering",
            address = "Greater Noida, Delhi NCR",
            rating = 4.5f,
            reviewCount = 112
        ),
        Business(
            id = "biz_006",
            ownerId = "owner_006",
            name = "LearnHub Academy",
            category = "Education & Tutoring",
            address = "Indirapuram, Ghaziabad",
            rating = 4.8f,
            reviewCount = 203
        )
    )
}
@Preview(showBackground = true, widthDp = 480, heightDp = 900)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onBusinessTap = { businessId ->
            println("Tapped business: $businessId")
        },
        onMyBookingsTap = {
            println("Tapped My Bookings")
        }
    )
}