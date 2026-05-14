package com.mk.datingapp.ui.main.mainScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(){
    val navController = rememberNavController()

    val items = listOf("home","explore", "chat", "profile")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .safeContentPadding()
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
    ){

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.fillMaxSize(
            )
        ){
            composable("home") { Text("HomeScreen") }
            composable("chat") { Text("Chat Screen") }
            composable("explore") { Text("Explore Screen") }
            composable("profile") { Text("Profile Screen") }
        }

        CustomBottomBar(navController,items,modifier = Modifier.align(Alignment.BottomCenter))

    }
}


@Composable
fun CustomBottomBar(
    navController: NavController ,
    items : List<String>,
    modifier : Modifier = Modifier
){
    val routeCurrent  = navController.currentBackStackEntryAsState().value?.destination?.route

    val infiniteTransition = rememberInfiniteTransition(label = "")

    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )


    Box(
        modifier = modifier
            .fillMaxWidth()
            .drawWithCache {

                onDrawBehind {
                    val strokeWidth = 2.dp.toPx()

                    val brush = Brush.sweepGradient(
                        colorStops = arrayOf(
                            (0.00f + progress) % 1f to Color(0xFFB388FF),
                            (0.10f + progress) % 1f to Color(0xFFA070FF),
                            (0.20f + progress) % 1f to Color(0xFF8E5CFF),
                            (0.30f + progress) % 1f to Color(0xFF6E3CFC),

                            (0.40f + progress) % 1f to Color(0xFF4F14F8),
                            (0.50f + progress) % 1f to Color(0xFF4F14F8),
                            (0.60f + progress) % 1f to Color(0xFF4F14F8),

                            (0.70f + progress) % 1f to Color(0xFF6E3CFC),
                            (0.80f + progress) % 1f to Color(0xFF9160FF),
                            (0.90f + progress) % 1f to Color(0xFFA984FF),

                            (1.00f + progress) % 1f to Color(0xFFB388FF)
                        ).sortedBy { it.first }
                            .toTypedArray(),
                        center = center
                    )
                        drawRoundRect(
                            brush = brush,
                            size = size,
                            cornerRadius = CornerRadius(100f, 100f),
                            style = Stroke(width = strokeWidth)
                        )

                }
            }
            .clip(RoundedCornerShape(30.dp))
            .background(Color(0xFFB388FF).copy(alpha = 0.3f))

    )
    {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
                .padding(vertical = 7.dp)
                .background(Color.Transparent, RoundedCornerShape(30.dp)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            items.forEach { route ->
                val isSelected = routeCurrent == route

                val scale = remember { Animatable(1f) }

                LaunchedEffect(isSelected) {
                    if (isSelected) {
                        scale.animateTo(
                            1.8f, // overshoot
                            animationSpec = tween(200)
                        )
                        scale.animateTo(
                            1.55f,
                            animationSpec = spring(
                                dampingRatio = 0.3f,
                                stiffness = 200f
                            )
                        )
                        scale.animateTo(
                            1.6f,
                            animationSpec = spring()
                        )
                    } else {
                        scale.animateTo(1f)
                    }
                }

                Icon(
                    imageVector = when (route) {
                        "home" -> Icons.Default.Home
                        "explore" -> Icons.Default.Explore
                        "chat" -> Icons.AutoMirrored.Filled.Chat
                        else -> Icons.Default.Person
                    },
                    contentDescription = route,
                    tint = if (isSelected) Color(0xFFB388FF) else Color.White,
                    modifier = modifier
                        .size(27.dp)
                        .scale(scale.value)
                        .clickable {
                            navController.navigate(route) {
                                popUpTo("home")
                                launchSingleTop = true
                            }
                        }
                )

            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview(){
    MainScreen()
}

