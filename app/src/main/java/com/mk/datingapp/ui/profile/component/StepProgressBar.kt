package com.mk.datingapp.ui.profile.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip

@Composable
fun StepProgressBar(
    currentStep: Int,
    totalSteps: Int = 4
){

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(totalSteps) { index ->

                val isActive = index < currentStep

                    val animatedColor by animateColorAsState(
                        targetValue = if (isActive) Color(0xFF9C27B0) else Color.DarkGray,
                        label = ""
                    )

                    val animatedWidth by animateDpAsState(
                        targetValue = if (index <= currentStep - 1) 70.dp else 40.dp,
                        label = ""
                    )

                Box(
                    modifier = Modifier
                        .height(6.dp)
                        .width(70.dp)
                        .clip(RoundedCornerShape(50))
                        .background(animatedColor)
                )
            }
        }
    }