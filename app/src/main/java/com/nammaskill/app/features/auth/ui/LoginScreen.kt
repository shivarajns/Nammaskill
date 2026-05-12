package com.nammaskill.app.features.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nammaskill.app.core.navigation.AppRoutes

@Composable
fun LoginScreen(
    navToHome: () -> Unit,
    navToAdmin: () -> Unit,
    onNavigateToRegister: () -> Unit
) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Welcome Back 👋",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(modifier = Modifier.padding(20.dp)) {

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {

                        val cleanEmail = email.trim()
                        val cleanPassword = password.trim()

                        if (cleanEmail.isEmpty() || cleanPassword.isEmpty()) {
                            error = "Fields cannot be empty"
                            return@Button
                        }

                        loading = true
                        error = ""

                        auth.signInWithEmailAndPassword(cleanEmail, cleanPassword)
                            .addOnSuccessListener { result ->

                                val uid = result.user?.uid

                                if (uid == null) {
                                    error = "Login failed"
                                    loading = false
                                    return@addOnSuccessListener
                                }

                                db.collection("users")
                                    .document(uid)
                                    .get()
                                    .addOnSuccessListener { doc ->

                                        loading = false

                                        val role = doc.getString("role") ?: "user"

                                        if (role == "admin") {
                                            navToAdmin()
                                        } else {
                                            navToHome()
                                        }
                                    }
                                    .addOnFailureListener {
                                        loading = false
                                        error = "Failed to fetch role"
                                    }
                            }
                            .addOnFailureListener {
                                loading = false
                                error = it.message ?: "Login failed"
                            }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !loading
                ) {
                    Text(if (loading) "Logging in..." else "Login")
                }

                Spacer(modifier = Modifier.height(10.dp))

                TextButton(
                    onClick = onNavigateToRegister,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Don't have an account? Register")
                }

                if (error.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}