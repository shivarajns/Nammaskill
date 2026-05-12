package com.nammaskill.app.features.home.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.nammaskill.app.core.model.Course
import com.nammaskill.app.core.repository.CourseRepository

@Composable
fun HomeTabScreen() {

    val auth = FirebaseAuth.getInstance()
    val userName = auth.currentUser?.email?.substringBefore("@") ?: "User"

    val repository = remember { CourseRepository() }

    var courses by remember { mutableStateOf<List<Course>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

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
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F172A),
                        Color(0xFF111827),
                        Color(0xFF1E293B)
                    )
                )
            )
            .padding(16.dp)
    ) {

        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + slideInVertically()
        ) {

            Column {

                GlassCard {

                    Column {

                        Text(
                            text = "Welcome Back 👋",
                            color = Color.White,
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

//                        Text(
//                            text = userName,
//                            color = Color.White,
//                            fontSize = 30.sp,
//                            fontWeight = FontWeight.Bold
//                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Upgrade your skills and build your future with NammaSkills.",
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            DashboardStatCard(
                title = "Courses",
                value = courses.size.toString(),
                modifier = Modifier.weight(1f),
                icon = {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            )

            DashboardStatCard(
                title = "Skills",
                value = "24+",
                modifier = Modifier.weight(1f),
                icon = {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Categories",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            CategoryChip("Technical")
            CategoryChip("Government")
            CategoryChip("IT")
            CategoryChip("Mechanical")
            CategoryChip("Electronics")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Featured Courses",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        if (loading) {

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }

        } else {

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                items(courses.take(5)) { course ->

                    FeaturedCourseCard(course)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        GlassCard {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        text = "Career Opportunities",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Complete skill programs and improve your chances of getting hired.",
                        color = Color.White.copy(alpha = 0.75f)
                    )
                }

                Icon(
                    imageVector = Icons.Default.Work,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun GlassCard(
    content: @Composable ColumnScope.() -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(28.dp)
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            content = content
        )
    }
}

@Composable
fun DashboardStatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit
) {

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(24.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {

            icon()

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = value,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = title,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun CategoryChip(text: String) {

    Surface(
        color = Color.White.copy(alpha = 0.1f),
        shape = RoundedCornerShape(50.dp)
    ) {

        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp)
        )
    }
}

@Composable
fun FeaturedCourseCard(course: Course) {

    Card(
        modifier = Modifier
            .width(260.dp)
            .clip(RoundedCornerShape(28.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.08f)
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        Color.White.copy(alpha = 0.12f),
                        RoundedCornerShape(18.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = course.name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = course.center,
                color = Color.White.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = course.duration,
                color = Color.White.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Explore")
            }
        }
    }
}
