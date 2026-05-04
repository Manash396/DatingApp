package com.mk.datingapp.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.mk.datingapp.ui.auth.screen.AnimScreen
import com.mk.datingapp.ui.auth.viewmodel.SessionViewModel
import com.mk.datingapp.ui.profile.screen.ProfileCreationScreen


@Composable
fun RootNavHost(
    sessionViewModel: SessionViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    val isLoggedIn by sessionViewModel.isLoggedIn.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = "profileCre"
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

        navigation(startDestination = "home", route = "main") {
            mainNavGraph(navController)
        }

        composable("profileCre") {
            ProfileCreationScreen(
                onCompletion = {
                    navController.navigate("main"){
                        popUpTo("profileCre"){ inclusive =  true}
                    }
                }
            )
        }

    }
}