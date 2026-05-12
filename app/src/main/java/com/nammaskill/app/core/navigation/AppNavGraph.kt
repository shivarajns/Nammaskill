package com.nammaskill.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nammaskill.app.features.admin.ui.ApplicationScreen
import com.nammaskill.app.features.auth.ui.LoginScreen
import com.nammaskill.app.features.auth.ui.RegisterScreen
import com.nammaskill.app.features.home.ui.MainScreen
import com.nammaskill.app.features.home.ui.screens.ApplicationFormScreen
import com.nammaskill.app.features.home.ui.screens.CourseDetailsScreen
import com.nammaskill.app.features.onboarding.ui.OnboardingScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(AppRoutes.ONBOARDING) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(AppRoutes.LOGIN) {
                        popUpTo(AppRoutes.ONBOARDING) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(AppRoutes.LOGIN) {
            LoginScreen(
                navToHome = {
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.LOGIN) {
                            inclusive = true
                        }
                    }
                },
                navToAdmin = {
                    navController.navigate(AppRoutes.ADMIN) {
                        popUpTo(AppRoutes.LOGIN) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(AppRoutes.REGISTER)
                }
            )
        }

        composable(AppRoutes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(AppRoutes.HOME) {
                        popUpTo(AppRoutes.REGISTER) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoutes.HOME) {
            MainScreen()
        }

        composable(AppRoutes.ADMIN) {
            ApplicationScreen()
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
                    backStackEntry.arguments?.getBoolean("jobGuarantee")
                        ?: false,

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
                    backStackEntry.arguments?.getBoolean("jobGuarantee")
                        ?: false,

                navController = navController
            )
        }
    }
}