package com.mk.datingapp.ui.auth.wel

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.mk.datingapp.R
import kotlinx.coroutines.delay

@Composable
fun AnimScreen(
    onFinish : () -> Unit
) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.logo_anim)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition
    )

//    LaunchedEffect(progress) {
//        if (progress == 1f){
//            onFinish()
//        }
//    }

    LaunchedEffect(Unit) {
        delay(3000)
        onFinish()
    }

    if (composition == null) {
        Log.d("Lottie", " Composition not loaded")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(56.dp)
        ,
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            iterations = 3,
            modifier = Modifier.size(100.dp)
        )
    }
}