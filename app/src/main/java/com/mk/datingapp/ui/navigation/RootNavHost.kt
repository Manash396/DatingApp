package com.mk.datingapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.mk.datingapp.ui.auth.wel.AnimScreen
import com.mk.datingapp.ui.session.SessionViewModel
import com.mk.datingapp.ui.profile.completed.ProfileCompletedScreen
import com.mk.datingapp.ui.profile.creation.ProfileCreationScreen


@Composable
fun RootNavHost(
    sessionViewModel: SessionViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    val isLoggedIn by sessionViewModel.isLoggedIn.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {

        composable("splash") {
            AnimScreen {
                if (isLoggedIn) {
//                    val dest  = if ()
                    navController.navigate("profileCre") {
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

        navigation(startDestination = "mainScreens", route = "main") {
            mainNavGraph(navController)
        }

        composable("profileCre") {
            ProfileCreationScreen(
                onCompletion = {
                    navController.navigate("profileDone"){
                        popUpTo("profileCre"){ inclusive =  true}
                    }
                }
            )
        }

        composable("profileDone") {
            ProfileCompletedScreen {
                navController.navigate("main"){
                    popUpTo("profileDone"){ inclusive =  true}
                }
            }
        }



    }
}