package com.nammaskill.app.features.home.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun CourseDetailsScreen(
    courseName: String,
    category: String,
    duration: String,
    center: String,
    jobGuarantee: Boolean,
    navController: NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F172A),
                        Color(0xFF111827)
                    )
                )
            )
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        IconButton(
            onClick = {
                navController.popBackStack()
            }
        ) {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = courseName,
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Build real-world skills and improve your career opportunities with this professional training program.",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(28.dp))

        DetailCard(
            title = "Category",
            value = category
        )

        Spacer(modifier = Modifier.height(16.dp))

        DetailCard(
            title = "Duration",
            value = duration,
            icon = {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        DetailCard(
            title = "Training Center",
            value = center,
            icon = {
                Icon(
                    imageVector = Icons.Default.Business,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        DetailCard(
            title = "Placement Support",
            value =
                if (jobGuarantee)
                    "Job Guarantee Available"
                else
                    "No Job Guarantee",
            icon = {
                Icon(
                    imageVector = Icons.Default.Work,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Skills You Will Learn",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        SkillChip("Practical Training")
        Spacer(modifier = Modifier.height(10.dp))
        SkillChip("Industry Knowledge")
        Spacer(modifier = Modifier.height(10.dp))
        SkillChip("Hands-on Projects")
        Spacer(modifier = Modifier.height(10.dp))
        SkillChip("Career Preparation")

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {

                navController.navigate(
                    "application_form/$courseName/$category/$duration/$center"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Apply For Course")
        }
    }
}

@Composable
fun DetailCard(
    title: String,
    value: String,
    icon: @Composable (() -> Unit)? = null
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.08f)
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (icon != null) {

                icon()

                Spacer(modifier = Modifier.width(14.dp))
            }

            Column {

                Text(
                    text = title,
                    color = Color.White.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = value,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SkillChip(text: String) {

    Surface(
        color = Color.White.copy(alpha = 0.08f),
        shape = MaterialTheme.shapes.large
    ) {

        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.padding(
                horizontal = 18.dp,
                vertical = 12.dp
            )
        )
    }
}