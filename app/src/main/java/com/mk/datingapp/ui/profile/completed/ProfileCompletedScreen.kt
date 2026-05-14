package com.mk.datingapp.ui.profile.completed


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.datingapp.utils.component.GradientButton
import com.mk.datingapp.utils.Util.getAlpha

@Composable
fun ProfileCompletedScreen(
    onGotIt: () -> Unit
) {

    val progress = remember { Animatable(0f) }
    var isCompleted by remember { mutableStateOf(false) }

    var waveAnimStart by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "")

    val waveScale1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val waveScale2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, delayMillis = 700, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val blurAmount by animateDpAsState(
        targetValue = if (isCompleted) 0.dp else 16.dp,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        )
    )

    val shake = remember { Animatable(0f) }

    LaunchedEffect(isCompleted) {
        if (isCompleted) {
            repeat(6) {
                shake.animateTo(10f, tween(50))
                shake.animateTo(-10f, tween(50))
            }
            shake.animateTo(0f)
            waveAnimStart = true
        }
    }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 2500,
                easing = FastOutSlowInEasing
            )
        )

        isCompleted = true
    }


    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0A0A0A),
            Color(0xFF120A1A),
            Color(0xFF1A0F2A)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .safeContentPadding()
            .padding(start = 20.dp, end = 20.dp)
    ) {

        // Progress text
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "SCANNING INTENT",
                color = Color.Gray,
                fontSize = 11.sp
            )

            Text(
                text = "${(progress.value * 100).toInt()} %",
                color = Color(0xFFB388FF),
                fontSize = 11.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Progress bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.DarkGray)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress.value)
                    .height(4.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(0xFF9C6BFF),
                                Color(0xFFB388FF)
                            )
                        )
                    )
            )
        }


        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .blur(blurAmount),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Glow circle background
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // waves


                // Outer soft glow
                Box(
                    modifier = Modifier
                        .size(220.dp)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFB388FF).copy(alpha = 0.2f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )

// Inner stronger glow
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFB388FF).copy(alpha = 0.5f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )

                Box(
                    modifier = Modifier
                        .size(220.dp*waveScale1)
                        .alpha(getAlpha(waveScale1))
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFB388FF).copy(alpha = 0.7f), // center glow
                                    Color(0xFFB388FF).copy(alpha = 0.4f), // mid fade
                                    Color(0xFFB388FF).copy(alpha = 0.1f)                      // outer fade
                                )
                            ),
                            shape = CircleShape
                        )
                )


                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .offset(x = shake.value.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1E1E2E)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = "Verified",
                        tint = Color(0xFFB388FF),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Title
            Text(
                text = buildAnnotatedString {
                    append("Profile ")
                    withStyle(style = SpanStyle(color = Color(0xFFB388FF))) {
                        append("Created")
                    }
                },
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Subtitle
            Text(
                text = "Our curators are scanning the feed to find your match.",
                fontSize = 14.sp,
                color = Color.LightGray,
                lineHeight = 15.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(30.dp))


        }

        Spacer(modifier = Modifier.height(40.dp))

        // Button
        GradientButton(
            text = "Got it",
            onClick = onGotIt,
            enabled = isCompleted
        )

        Spacer(modifier = Modifier.height(33.dp))

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileCompletedScreenPreview() {
    ProfileCompletedScreen(
        onGotIt = {}
    )
}