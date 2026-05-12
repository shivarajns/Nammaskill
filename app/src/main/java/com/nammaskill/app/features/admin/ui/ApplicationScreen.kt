package com.nammaskill.app.features.admin.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nammaskill.app.core.model.Application
import com.nammaskill.app.core.repository.CourseRepository

@Composable
fun ApplicationScreen() {

    val context = LocalContext.current
    val repository = remember { CourseRepository() }

    var applications by remember { mutableStateOf<List<Application>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {

        repository.getApplications(
            onSuccess = {
                applications = it
                loading = false
            },
            onFailure = {
                loading = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Applications",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (loading) {
            CircularProgressIndicator()
        } else {

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                items(applications) { app ->

                    val isPending = app.status == "pending"

                    Card(modifier = Modifier.fillMaxWidth()) {

                        Column(modifier = Modifier.padding(16.dp)) {

                            Text("Email: ${app.userEmail}")
                            Text("Course: ${app.courseName}")
                            Text("Center: ${app.center}")

                            Row {
                                Text("Status: ")
                                StatusBadge(app.status)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            if (isPending) {

                                Row {

                                    Button(onClick = {
                                        repository.updateApplicationStatus(
                                            documentId = app.id,
                                            newStatus = "approved",
                                            onSuccess = {

                                                Toast.makeText(
                                                    context,
                                                    "Approved Successfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                            },
                                            onFailure = {
                                                Toast.makeText(
                                                    context,
                                                    "Failed to update",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )
                                    }) {
                                        Text("Approve")
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Button(onClick = {
                                        repository.updateApplicationStatus(
                                            documentId = app.id,
                                            newStatus = "rejected",
                                            onSuccess = {

                                                Toast.makeText(
                                                    context,
                                                    "Rejected Successfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                            },
                                            onFailure = {
                                                Toast.makeText(
                                                    context,
                                                    "Failed to update",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )
                                    }) {
                                        Text("Reject")
                                    }
                                }

                            } else {

                                Text(
                                    text = if (app.status == "approved")
                                        "✅ Already Approved"
                                    else
                                        "❌ Already Rejected",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {

    val (color, text) = when (status.lowercase()) {

        "approved" -> MaterialTheme.colorScheme.primary to "Approved"
        "rejected" -> MaterialTheme.colorScheme.error to "Rejected"
        else -> MaterialTheme.colorScheme.tertiary to "Pending"
    }

    Surface(
        color = color.copy(alpha = 0.2f),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = color
        )
    }
}