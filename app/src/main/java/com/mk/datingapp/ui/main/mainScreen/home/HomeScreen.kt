package com.mk.datingapp.ui.main.mainScreen.home

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.datingapp.R
import com.mk.datingapp.ui.theme.labelColor
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {

    val profiles = remember {
        mutableStateListOf(
            Profile(1, "Elena", 24, R.drawable.girl1),
            Profile(2, "Sophia", 22, R.drawable.girl2),
            Profile(3, "Mia", 25, R.drawable.girl3)
        )
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            Text(
                text = "Ethereal",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = labelColor,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Box{
                // notification icon
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .align(Alignment.TopEnd)
                        .background(labelColor, shape = CircleShape)
                )
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "notification",
                    tint = labelColor,
                    modifier = Modifier
                        .clickable{

                        }
                )
            }
        }

        // card view

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            profiles.take(3).reversed().forEach { profile ->

                key(profile.id) {
                    SwipeCard(
                        profile = profile,
                        onSwipe = {
                            profiles.remove(profile)
                        }
                    )
                }
            }
        }

    }
}

@Composable
fun SwipeCard(
    profile: Profile,
    onSwipe: () -> Unit
){
    val scope  = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }

    val rotation = offsetX.value / 60f

    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(500.dp)
            .offset { IntOffset(offsetX.value.toInt(), 0) }
            .rotate(rotation)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Transparent, RoundedCornerShape(20.dp))
            .pointerInput(Unit) {

                detectDragGestures(
                    onDrag = { _, dragAmount ->
                        scope.launch {
                            offsetX.snapTo(offsetX.value + dragAmount.x)
                        }
                    },
                    onDragEnd = {
                        scope.launch {
                            when {
                                offsetX.value > 300 -> {
                                    offsetX.animateTo(1000f)
                                    onSwipe()
                                }

                                offsetX.value < -300 -> {
                                    offsetX.animateTo(-1000f)
                                    onSwipe()
                                }

                                else -> {
                                    offsetX.animateTo(0f)
                                }
                            }
                        }
                    }
                )
            }
    ){


        // MAIN IMAGE
        Image(
            painter = painterResource(id = profile.imgRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        //  GRADIENT OVERLAY (for text readability)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black)
                    )
                )
        )

        //  SECOND IMAGE / BADGE (top corner)
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = labelColor
            )
        }

        //  TEXT
        Text(
            text = "${profile.name}, ${profile.age}",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}
data class Profile(
    val id: Int,
    val name: String,
    val age: Int,
    val imgRes: Int
)