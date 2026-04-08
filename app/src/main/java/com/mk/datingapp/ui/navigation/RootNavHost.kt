package com.mk.datingapp.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.mk.datingapp.ui.auth.screen.AnimScreen


@Composable
fun RootNavHost(isLoggedIn: Boolean) {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        composable("splash") {
            AnimScreen {
                if (isLoggedIn) {
                    navController.navigate("main") {
                        popUpTo("splash") { inclusive = true }
                    }
                } else {
                    navController.navigate("auth") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            }
        }

        navigation(startDestination = "wel", route = "auth") {
            authNavGraph(navController)
        }

        navigation(startDestination = "profile", route = "main") {
            mainNavGraph(navController)
        }


    }
}