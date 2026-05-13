package com.mk.datingapp.ui.profile.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun StepProgressBar(
    currentStep: Int,
    totalSteps: Int = 4
){

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(totalSteps) { index ->

                val isActive = index == currentStep-1

                    val animatedColor by animateColorAsState(
                        targetValue = if (isActive) Color(0xFF9C27B0) else Color.DarkGray,
                        label = "",
                        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                    )

                    val animatedWidth by animateDpAsState(
                        targetValue = if (index <= currentStep - 1) 60.dp else 40.dp,
                        label = ""
                    )

                val scale by animateFloatAsState(
                    targetValue = if (isActive) 1.5f else 1f,
                    label = "",
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                )

                Box(
                    modifier = Modifier
                        .height(6.dp)
                        .weight(1f)
                        .graphicsLayer{
                            scaleY = scale
                        }
                        .shadow(if (isActive) 16.dp else 0.dp , RoundedCornerShape(50))
                        .clip(RoundedCornerShape(50))
                        .background(animatedColor),
                )
            }
        }
    }