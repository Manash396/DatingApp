package com.mk.datingapp.ui.navigation

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.mk.datingapp.ui.auth.component.rememberGoogleSignInLauncher
import com.mk.datingapp.ui.auth.event.WelScreenEvent
import com.mk.datingapp.ui.auth.screen.LoginScreen
import com.mk.datingapp.ui.auth.screen.OtpScreen
import com.mk.datingapp.ui.auth.screen.RegisterScreen
import com.mk.datingapp.ui.auth.screen.WelScreen

fun NavGraphBuilder.authNavGraph(navController: NavController) {


    composable("login") {

        LoginScreen({
            navController.navigate("main") {
                popUpTo("auth") { inclusive = true }
            }
        }, onGoogleClick = {

        }, navController)
    }

    composable("signup") {
        RegisterScreen(
            onRegisterClick = {

                navController.navigate("verify")
            },
            navController
        )
    }

    composable("wel") {

        WelScreen(
            onEvent = { event ->
                when (event) {
                    WelScreenEvent.OnEmailClick -> {
                        navController.navigate("signup")
                    }

                    WelScreenEvent.OnGetStartedClick -> {

                    }

                    WelScreenEvent.onGoogleSuccess -> {

                    }


                    WelScreenEvent.OnLoginClick -> {
                        navController.navigate("login")
                        {
                            // keeping the wel screen (start destination of a nested graph)
                            popUpTo("wel") { inclusive = false }
                        }
                    }

                    WelScreenEvent.OnNavigateToMain -> {
                        navController.navigate("main")
                        {
                            popUpTo("wel") { inclusive = false }
                        }
                    }
                    WelScreenEvent.OnNavigateToProfile -> {
                        navController.navigate("profileCre")
                        {
                            popUpTo("wel") { inclusive = false }
                        }
                    }
                }
            },

        )

    }

    composable("verify") {
        OtpScreen({
            navController.navigate("profileCre") {
                popUpTo("auth") { inclusive = true }
            }
        }, navController = navController)
    }

}