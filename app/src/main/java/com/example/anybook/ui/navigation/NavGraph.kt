package com.example.anybook.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.anybook.data.model.UserRole
import com.example.anybook.ui.auth.ClientLoginScreen
import com.example.anybook.ui.auth.ClientSignUpScreen
import com.example.anybook.ui.auth.OwnerLoginScreen
import com.example.anybook.ui.auth.OwnerSignUpScreen
import com.example.anybook.ui.auth.RoleSelectorScreen

object Screen {
    const val RoleSelector = "role_selector"
    const val ClientLogin = "client_login"
    const val OwnerLogin = "owner_login"
    const val ClientSignUp = "client_signup"
    const val OwnerSignUp = "owner_signup"
    const val ClientPlaceholder = "client_placeholder" // TODO: replace with HomeScreen once built
    const val OwnerPlaceholder = "owner_placeholder"   // TODO: replace with BusinessSetup -> Dashboard once built
}

@Composable
fun NavGraph(navController: NavHostController = rememberNavController(),modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Screen.RoleSelector, modifier = Modifier) {

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
                    navController.navigate(Screen.ClientPlaceholder) {
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
                    navController.navigate(Screen.ClientPlaceholder) {
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
                    navController.navigate(Screen.OwnerPlaceholder) {
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
                    navController.navigate(Screen.OwnerPlaceholder) {
                        popUpTo(Screen.RoleSelector) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // TODO: temporary stand-in for HomeScreen
        composable(Screen.ClientPlaceholder) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Client Home - Coming Soon")
            }
        }

        // TODO: temporary stand-in for BusinessSetupScreen -> DashboardScreen
        composable(Screen.OwnerPlaceholder) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Owner Dashboard - Coming Soon")
            }
        }
    }
}