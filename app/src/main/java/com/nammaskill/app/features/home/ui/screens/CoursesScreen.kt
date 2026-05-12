package com.nammaskill.app.features.home.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.nammaskill.app.core.model.Application
import com.nammaskill.app.core.model.Course
import com.nammaskill.app.core.navigation.AppRoutes
import com.nammaskill.app.core.repository.CourseRepository
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun CoursesScreen(
    navController: NavHostController
) {

    val repository = remember { CourseRepository() }

    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid ?: ""

    var courses by remember { mutableStateOf<List<Course>>(emptyList()) }
    var applications by remember { mutableStateOf<List<Application>>(emptyList()) }

    var loading by remember { mutableStateOf(true) }

    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All", "Technical", "Government", "IT", "Mechanical")

    LaunchedEffect(Unit) {

        repository.getCourses(
            onSuccess = {
                courses = it
                loading = false
            },
            onFailure = {
                loading = false
            }
        )

        repository.getApplications(
            onSuccess = {
                applications = it
            },
            onFailure = {}
        )
    }

    val filteredCourses = courses.filter { course ->
        val matchesSearch = course.name.contains(searchText, ignoreCase = true)
        val matchesCategory =
            selectedCategory == "All" || course.category.equals(selectedCategory, true)
        matchesSearch && matchesCategory
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF0F172A),
                        Color(0xFF111827)
                    )
                )
            )
            .padding(16.dp)
    ) {

        Text(
            text = "Explore Courses",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            label = { Text("Search Courses") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            categories.forEach { category ->

                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    label = { Text(category) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (loading) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {

                items(filteredCourses) { course ->

                    val application = applications.find {
                        it.userId == userId && it.courseName == course.name
                    }

                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + slideInVertically()
                    ) {

                        CourseCardModern(
                            course = course,
                            application = application,
                            onOpenDetails = {

                                val name = URLEncoder.encode(course.name, StandardCharsets.UTF_8.toString())
                                val category = URLEncoder.encode(course.category, StandardCharsets.UTF_8.toString())
                                val duration = URLEncoder.encode(course.duration, StandardCharsets.UTF_8.toString())
                                val center = URLEncoder.encode(course.center, StandardCharsets.UTF_8.toString())

                                navController.navigate(
                                    "${AppRoutes.COURSE_DETAILS}/$name/$category/$duration/$center/${course.jobGuarantee}"
                                )
                            },
                            onApply = {

                                val name = URLEncoder.encode(course.name, StandardCharsets.UTF_8.toString())
                                val category = URLEncoder.encode(course.category, StandardCharsets.UTF_8.toString())
                                val duration = URLEncoder.encode(course.duration, StandardCharsets.UTF_8.toString())
                                val center = URLEncoder.encode(course.center, StandardCharsets.UTF_8.toString())

                                navController.navigate(
                                    "${AppRoutes.APPLICATION_FORM}/$name/$category/$duration/$center/${course.jobGuarantee}"
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CourseCardModern(
    course: Course,
    application: Application?,
    onApply: () -> Unit,
    onOpenDetails: () -> Unit
) {

    Card(
        onClick = onOpenDetails,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.08f)
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = course.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(course.category, color = Color.White.copy(alpha = 0.7f))
            Text(course.duration, color = Color.White.copy(alpha = 0.7f))
            Text(course.center, color = Color.White.copy(alpha = 0.7f))

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = if (course.jobGuarantee) "Job Guarantee" else "No Job Guarantee",
                color = if (course.jobGuarantee) Color(0xFF22C55E) else Color(0xFFEF4444)
            )

            Spacer(modifier = Modifier.height(14.dp))

            val status = application?.status?.lowercase()

            when (status) {

                "pending" -> {
                    Button(
                        onClick = {},
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Pending")
                    }
                }

                "approved" -> {
                    Button(
                        onClick = {},
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Approved")
                    }
                }

                "rejected" -> {
                    Button(
                        onClick = {},
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Rejected")
                    }
                }

                else -> {
                    Button(
                        onClick = onApply,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Apply Now")
                    }
                }
            }
        }
    }
}