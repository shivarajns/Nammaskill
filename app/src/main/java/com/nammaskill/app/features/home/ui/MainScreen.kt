package com.nammaskill.app.features.home.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nammaskill.app.core.navigation.AppRoutes
import com.nammaskill.app.core.navigation.NavItem
import com.nammaskill.app.features.home.ui.screens.ApplicationFormScreen
import com.nammaskill.app.features.home.ui.screens.CourseDetailsScreen
import com.nammaskill.app.features.home.ui.screens.CoursesScreen
import com.nammaskill.app.features.home.ui.screens.HomeTabScreen
import com.nammaskill.app.features.home.ui.screens.ProfileScreen

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = backStackEntry?.destination?.route

    val items = listOf(
        NavItem("Home", Icons.Default.Home, "home_tab"),
        NavItem("Courses", Icons.Default.Menu, "courses_tab"),
        NavItem("Profile", Icons.Default.Person, "profile_tab")
    )

    Scaffold(

        bottomBar = {

            val hideBottomBar =
                currentRoute?.startsWith(AppRoutes.COURSE_DETAILS) == true ||
                        currentRoute?.startsWith(AppRoutes.APPLICATION_FORM) == true

            if (!hideBottomBar) {

                NavigationBar {

                    items.forEach { item ->

                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {

                                navController.navigate(item.route) {

                                    popUpTo(navController.graph.startDestinationId)

                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            label = {
                                Text(item.label)
                            }
                        )
                    }
                }
            }
        }

    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = "home_tab",
            modifier = Modifier.padding(paddingValues)
        ) {

            composable("home_tab") {
                HomeTabScreen()
            }

            composable("courses_tab") {

                CoursesScreen(
                    navController = navController
                )
            }

            composable("profile_tab") {

                ProfileScreen(
                    navController = navController
                )
            }

            composable(
                route =
                    "${AppRoutes.COURSE_DETAILS}/{courseName}/{category}/{duration}/{center}/{jobGuarantee}",

                arguments = listOf(

                    navArgument("courseName") {
                        type = NavType.StringType
                    },

                    navArgument("category") {
                        type = NavType.StringType
                    },

                    navArgument("duration") {
                        type = NavType.StringType
                    },

                    navArgument("center") {
                        type = NavType.StringType
                    },

                    navArgument("jobGuarantee") {
                        type = NavType.BoolType
                    }
                )
            ) { backStackEntry ->

                CourseDetailsScreen(
                    courseName =
                        backStackEntry.arguments?.getString("courseName") ?: "",

                    category =
                        backStackEntry.arguments?.getString("category") ?: "",

                    duration =
                        backStackEntry.arguments?.getString("duration") ?: "",

                    center =
                        backStackEntry.arguments?.getString("center") ?: "",

                    jobGuarantee =
                        backStackEntry.arguments?.getBoolean("jobGuarantee") ?: false,

                    navController = navController
                )
            }

            composable(
                route =
                    "${AppRoutes.APPLICATION_FORM}/{courseName}/{category}/{duration}/{center}/{jobGuarantee}",

                arguments = listOf(

                    navArgument("courseName") {
                        type = NavType.StringType
                    },

                    navArgument("category") {
                        type = NavType.StringType
                    },

                    navArgument("duration") {
                        type = NavType.StringType
                    },

                    navArgument("center") {
                        type = NavType.StringType
                    },

                    navArgument("jobGuarantee") {
                        type = NavType.BoolType
                    }
                )
            ) { backStackEntry ->

                ApplicationFormScreen(
                    courseName =
                        backStackEntry.arguments?.getString("courseName") ?: "",

                    category =
                        backStackEntry.arguments?.getString("category") ?: "",

                    duration =
                        backStackEntry.arguments?.getString("duration") ?: "",

                    center =
                        backStackEntry.arguments?.getString("center") ?: "",

                    jobGuarantee =
                        backStackEntry.arguments?.getBoolean("jobGuarantee") ?: false,

                    navController = navController
                )
            }
        }
    }
}