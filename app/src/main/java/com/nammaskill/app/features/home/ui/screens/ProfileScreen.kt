package com.nammaskill.app.features.home.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(
    navController: NavHostController
) {

    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                // 🔥 1. Sign out Firebase user
                auth.signOut()

                // 🔥 2. SAFE NAVIGATION RESET (NO Int, NO invalid route)
                navController.popBackStack(
                    route = "home_tab",
                    inclusive = true
                )

                // ⚠️ IMPORTANT:
                // Do NOT navigate to login/onboarding here
                // MainActivity handles routing based on auth state

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}