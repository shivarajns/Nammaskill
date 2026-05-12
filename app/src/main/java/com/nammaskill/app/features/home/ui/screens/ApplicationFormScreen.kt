package com.nammaskill.app.features.home.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nammaskill.app.core.repository.CourseRepository

@Composable
fun ApplicationFormScreen(
    courseName: String,
    category: String,
    duration: String,
    center: String,
    jobGuarantee: Boolean,
    navController: NavHostController
) {

    val context = LocalContext.current
    val repository = remember { CourseRepository() }

    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var qualification by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var skills by remember { mutableStateOf("") }

    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(
            text = "Course Application",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Course: $courseName")
        Text(text = "Category: $category")
        Text(text = "Duration: $duration")
        Text(text = "Center: $center")

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = {
                fullName = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Full Name")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Phone Number")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = qualification,
            onValueChange = {
                qualification = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Qualification")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = age,
            onValueChange = {
                age = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Age")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = gender,
            onValueChange = {
                gender = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Gender")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = district,
            onValueChange = {
                district = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("District")
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = skills,
            onValueChange = {
                skills = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Skills")
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                when {

                    fullName.isBlank() -> {
                        Toast.makeText(
                            context,
                            "Enter Full Name",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    phoneNumber.isBlank() -> {
                        Toast.makeText(
                            context,
                            "Enter Phone Number",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    qualification.isBlank() -> {
                        Toast.makeText(
                            context,
                            "Enter Qualification",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    age.isBlank() -> {
                        Toast.makeText(
                            context,
                            "Enter Age",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    gender.isBlank() -> {
                        Toast.makeText(
                            context,
                            "Enter Gender",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    district.isBlank() -> {
                        Toast.makeText(
                            context,
                            "Enter District",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    skills.isBlank() -> {
                        Toast.makeText(
                            context,
                            "Enter Skills",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {

                        repository.applyForCourse(
                            courseName = courseName,
                            center = center,
                            fullName = fullName,
                            phoneNumber = phoneNumber,
                            qualification = qualification,
                            age = age,
                            gender = gender,
                            district = district,
                            skills = skills,
                            category = category,
                            duration = duration,
                            onSuccess = {

                                Toast.makeText(
                                    context,
                                    "Application Submitted",
                                    Toast.LENGTH_SHORT
                                ).show()

                                navController.popBackStack()
                            },
                            onFailure = {

                                Toast.makeText(
                                    context,
                                    it.message ?: "Failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            Text("Submit Application")
        }
    }
}