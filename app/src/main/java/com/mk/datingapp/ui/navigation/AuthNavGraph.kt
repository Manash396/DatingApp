package com.mk.datingapp.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mk.datingapp.ui.auth.screen.LoginScreen
import com.mk.datingapp.ui.auth.screen.WelScreen

fun NavGraphBuilder.authNavGraph(navController: NavController){

    composable("login"){
        LoginScreen({}, navController)
    }

    composable("signup"){

    }

    composable("wel"){
        WelScreen(
            onGetStarted = {} ,
            onGoogleClick = {} ,
            onEmailClick = {},
            navController
        )
    }

}