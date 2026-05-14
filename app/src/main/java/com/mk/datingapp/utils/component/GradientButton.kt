package com.mk.datingapp.utils.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp

@Composable
fun GradientButton(text: String, onClick: () -> Unit, enabled:Boolean = true) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(),
        enabled = enabled
    ) {
        val startColor by animateColorAsState(
            targetValue = if (enabled) Color(0xFF8E2DE2) else Color.LightGray,
            animationSpec = tween(1000),
            label = ""
        )

        val endColor by animateColorAsState(
            targetValue = if (enabled) Color(0xFF4A00E0) else Color.Gray,
            animationSpec = tween(1000),
            label = ""
        )
        var boxWidth by remember { mutableFloatStateOf(0f) }

        val infiniteTransition = rememberInfiniteTransition(label = "")

        val offsetX by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = boxWidth,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = ""
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged {
                    boxWidth = it.width.toFloat()
                }
                .background(
                    if (enabled) {
                        Brush.linearGradient(
                            colors = listOf(startColor, endColor),
                            start = Offset(offsetX, 0f),
                            end = Offset(offsetX + boxWidth, 0f)
                        )
                    }else{
                        Brush.horizontalGradient(
                            listOf(startColor, endColor)
                        )
                    },
                    shape = RoundedCornerShape(50)
                )
                .padding(vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text, color = Color.White)
        }
    }
}