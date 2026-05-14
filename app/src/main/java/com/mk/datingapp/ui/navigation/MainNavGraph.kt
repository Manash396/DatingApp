package com.mk.datingapp.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mk.datingapp.ui.main.mainScreen.MainScreen

fun NavGraphBuilder.mainNavGraph(navController: NavController){
    composable("mainScreens") {
        MainScreen()
    }

    composable("notification") {

    }

    composable("profileView") {

    }
}