package com.nammaskill.app.features.home.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {

    val categories = listOf(
        "Electrician",
        "Sewing",
        "Welding",
        "Coding",
        "Carpentry",
        "Mobile Repair"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        // HEADER
        Text(
            text = "Welcome 👋",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Explore skill training near you",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        // CATEGORY CHIPS
        Text(
            text = "Popular Trades",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(categories) { item ->
                AssistChip(
                    onClick = { },
                    label = { Text(item) }
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // QUICK ACTION CARDS
        Text(
            text = "Quick Actions",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text("🎯 Find Nearby Skill Centers")
                Spacer(modifier = Modifier.height(6.dp))
                Text("📚 View Active Courses")
                Spacer(modifier = Modifier.height(6.dp))
                Text("📞 Request Callback from Trainer")
            }
        }
    }
}