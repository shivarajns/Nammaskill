package com.nammaskill.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.nammaskill.app.core.navigation.AppNavGraph
import com.nammaskill.app.core.navigation.AppRoutes
import com.nammaskill.app.core.theme.NammaSkillTheme

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setContent {
            NammaSkillTheme {

                val navController = rememberNavController()

                val currentUser = auth.currentUser

                val startDestination =
                    if (currentUser != null) AppRoutes.HOME
                    else AppRoutes.ONBOARDING

                AppNavGraph(
                    navController = navController,
                    startDestination = startDestination
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser

        if (user == null) {
            auth.signOut()
        }
    }
}