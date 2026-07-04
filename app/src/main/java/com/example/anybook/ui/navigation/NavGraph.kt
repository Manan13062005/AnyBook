package com.example.anybook.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.anybook.data.model.UserRole
import com.example.anybook.data.repository.MockBookingRepository
import com.example.anybook.ui.auth.ClientLoginScreen
import com.example.anybook.ui.auth.ClientSignUpScreen
import com.example.anybook.ui.auth.OwnerLoginScreen
import com.example.anybook.ui.auth.OwnerSignUpScreen
import com.example.anybook.ui.auth.RoleSelectorScreen
import com.example.anybook.ui.client.BookingScreen
import com.example.anybook.ui.client.BookingScreenCallbacks
import com.example.anybook.ui.client.BookingSuccessScreen
import com.example.anybook.ui.client.BusinessDetailScreen
import com.example.anybook.ui.client.ClientProfileCallbacks
import com.example.anybook.ui.client.ClientProfileScreen
import com.example.anybook.ui.client.ConfirmationScreen
import com.example.anybook.ui.client.ConfirmationScreenCallbacks
import com.example.anybook.ui.client.HomeScreen
import com.example.anybook.ui.client.MyBookingsScreen
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

object Screen {
    // ============ AUTH SCREENS ============
    const val RoleSelector = "role_selector"
    const val ClientLogin = "client_login"
    const val OwnerLogin = "owner_login"
    const val ClientSignUp = "client_signup"
    const val OwnerSignUp = "owner_signup"

    // ============ CLIENT FLOW ============
    const val ClientHome = "client_home"
    const val BusinessDetail = "business_detail/{businessId}"
    const val BookingScreen = "booking_screen"
    const val ConfirmationScreen = "confirmation_screen"
    const val BookingSuccess = "booking_success"

    // ============ OWNER FLOW ============
    const val OwnerDashboard = "owner_dashboard"
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.RoleSelector,
        modifier = modifier
    ) {
        // ============ AUTHENTICATION FLOW ============

        composable(Screen.RoleSelector) {
            RoleSelectorScreen(
                onRoleSelected = { role ->
                    if (role == UserRole.CLIENT) {
                        navController.navigate(Screen.ClientLogin)
                    } else {
                        navController.navigate(Screen.OwnerLogin)
                    }
                }
            )
        }

        composable(Screen.ClientLogin) {
            ClientLoginScreen(
                onLoginClick = { _, _, _ ->
                    navController.navigate(Screen.ClientHome) {
                        popUpTo(Screen.RoleSelector) { inclusive = true }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(Screen.ClientSignUp)
                }
            )
        }

        composable(Screen.ClientSignUp) {
            ClientSignUpScreen(
                onCreateAccountClick = { _, _, _, _ ->
                    navController.navigate(Screen.ClientHome) {
                        popUpTo(Screen.RoleSelector) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.OwnerLogin) {
            OwnerLoginScreen(
                onLoginClick = { _, _, _ ->
                    navController.navigate(Screen.OwnerDashboard) {
                        popUpTo(Screen.RoleSelector) { inclusive = true }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(Screen.OwnerSignUp)
                }
            )
        }

        composable(Screen.OwnerSignUp) {
            OwnerSignUpScreen(
                onRegisterBusinessClick = { _, _, _, _, _ ->
                    navController.navigate(Screen.OwnerDashboard) {
                        popUpTo(Screen.RoleSelector) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // ============ CLIENT MAIN FLOW (WITH BOTTOM NAV) ============

        composable(Screen.ClientHome) {
            ClientMainFlowWithNavBar(navController = navController)
        }

        // ============ CLIENT BOOKING FLOW ============

        composable(Screen.BusinessDetail) { backStackEntry ->
            val businessId = backStackEntry.arguments?.getString("businessId") ?: "1"
            var selectedServiceId by remember { mutableStateOf("") }
            var selectedDateTime by remember { mutableStateOf("") }

            BusinessDetailScreen(
                businessId = businessId,
                onBack = { navController.popBackStack() },
                onNext = { serviceId, dateTime ->
                    selectedServiceId = serviceId
                    selectedDateTime = dateTime
                    navController.navigate(Screen.BookingScreen)
                }
            )
        }

        composable(Screen.BookingScreen) {
            var customerName by remember { mutableStateOf("") }
            var customerPhone by remember { mutableStateOf("") }
            var selectedDate by remember { mutableStateOf("") }
            var selectedTime by remember { mutableStateOf("") }

            BookingScreen(
                businessName = "Sample Business",
                currentName = customerName,
                currentPhone = customerPhone,
                currentDate = selectedDate,
                currentTime = selectedTime,
                callbacks = BookingScreenCallbacks(
                    onNameChange = { customerName = it },
                    onPhoneChange = { customerPhone = it },
                    onDateSelected = { selectedDate = it },
                    onTimeSelected = { selectedTime = it },
                    onNextClick = {
                        navController.navigate(Screen.ConfirmationScreen)
                    },
                    onBackClick = { navController.popBackStack() }
                )
            )
        }

        composable(Screen.ConfirmationScreen) {
            ConfirmationScreen(
                businessName = "Sample Business",
                selectedDate = "Mon, 30 Jun",
                selectedTime = "2:00 PM",
                customerName = "Customer Name",
                customerPhone = "+91 98765 43210",
                callbacks = ConfirmationScreenCallbacks(
                    onConfirmClick = {
                        navController.navigate(Screen.BookingSuccess)
                    },
                    onCancelClick = { navController.popBackStack() }
                )
            )
        }

        composable(Screen.BookingSuccess) {
            val booking = MockBookingRepository.createMockBooking()
            BookingSuccessScreen(
                booking = booking,
                onViewBooking = {
                    navController.navigate(Screen.ClientHome)
                },
                onCancelBooking = {
                    navController.navigate(Screen.ClientHome) {
                        popUpTo(Screen.BookingSuccess) { inclusive = true }
                    }
                }
            )
        }

        // ============ OWNER FLOW ============

        composable(Screen.OwnerDashboard) {
            OwnerDashboardPlaceholder()
        }
    }
}

@Composable
fun ClientMainFlowWithNavBar(navController: NavHostController) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Content based on selected tab
        Column(modifier = Modifier.weight(1f)) {
            when (selectedTab) {
                0 -> {
                    HomeScreen(
                        onBusinessTap = { businessId ->
                            navController.navigate("business_detail/$businessId")
                        },
                        onMyBookingsTap = {
                            selectedTab = 1
                        }
                    )
                }
                1 -> {
                    MyBookingsScreen(
                        bookings = MockBookingRepository.getMockBookings()
                    )
                }
                2 -> {
                    ClientProfileScreen(
                        name = "Ananya Sharma",
                        email = "ananya.sharma@email.com",
                        phone = "+91 98765 43210",
                        callbacks = ClientProfileCallbacks(
                            onBackClick = { selectedTab = 0 },
                            onMyBookingsClick = { selectedTab = 1 },
                            onSwitchToOwner = {
                                navController.navigate(Screen.RoleSelector) {
                                    popUpTo(0) { inclusive = true }
                                }
                            },
                            onLogout = {
                                navController.navigate(Screen.RoleSelector) {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        )
                    )
                }
            }
        }

        // Custom Bottom Navigation Bar
        androidx.compose.material3.Surface(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            shadowElevation = 8.dp
        ) {
            androidx.compose.foundation.layout.Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Home Tab
                androidx.compose.material3.Button(
                    onClick = { selectedTab = 0 },
                    modifier = Modifier.weight(1f),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab == 0) Color(0xFF1565C0) else Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Home",
                        tint = if (selectedTab == 0) Color.White else Color(0xFF1565C0)
                    )
                    Text(
                        "Home",
                        fontSize = 11.sp,
                        color = if (selectedTab == 0) Color.White else Color(0xFF1565C0)
                    )
                }

                // My Bookings Tab
                androidx.compose.material3.Button(
                    onClick = { selectedTab = 1 },
                    modifier = Modifier.weight(1f),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab == 1) Color(0xFF1565C0) else Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.List,
                        contentDescription = "My Bookings",
                        tint = if (selectedTab == 1) Color.White else Color(0xFF1565C0)
                    )
                    Text(
                        "My Bookings",
                        fontSize = 11.sp,
                        color = if (selectedTab == 1) Color.White else Color(0xFF1565C0)
                    )
                }

                // Profile Tab
                androidx.compose.material3.Button(
                    onClick = { selectedTab = 2 },
                    modifier = Modifier.weight(1f),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = if (selectedTab == 2) Color(0xFF1565C0) else Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Profile",
                        tint = if (selectedTab == 2) Color.White else Color(0xFF1565C0)
                    )
                    Text(
                        "Profile",
                        fontSize = 11.sp,
                        color = if (selectedTab == 2) Color.White else Color(0xFF1565C0)
                    )
                }
            }
        }
    }
}

@Composable
fun OwnerDashboardPlaceholder() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Owner Dashboard - Coming Soon",
            fontSize = 18.sp,
            color = Color.Gray
        )
    }
}