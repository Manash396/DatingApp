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
        val (client, launcher) = rememberGoogleSignInLauncher { email, name ->
            navController.navigate("main") {
                popUpTo("auth") { inclusive = true }
            }
        }

        LoginScreen({
            navController.navigate("main") {
                popUpTo("auth") { inclusive = true }
            }
        }, onGoogleClick = {
            launcher.launch(client.signInIntent)
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
        val (client, launcher) = rememberGoogleSignInLauncher { email, name ->
            navController.navigate("profile") {
                popUpTo("auth") { inclusive = true }
            }
        }

        WelScreen(
            onEvent = { event ->
                when (event) {
                    WelScreenEvent.OnEmailClick -> {
                        navController.navigate("signup")
                    }

                    WelScreenEvent.OnGetStartedClick -> {

                    }

                    WelScreenEvent.OnGoogleClick -> {
                        launcher.launch(client.signInIntent)
                    }

                    WelScreenEvent.OnLoginClick -> {
                        navController.navigate("login")
                        {
                            // keeping the wel screen (start destination of a nested graph)
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