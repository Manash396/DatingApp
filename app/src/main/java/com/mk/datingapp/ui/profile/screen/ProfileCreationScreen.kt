package com.mk.datingapp.ui.profile.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.datingapp.ui.profile.component.StepOneScreen
import com.mk.datingapp.ui.profile.component.StepProgressBar

@Composable
fun ProfileCreationScreen(
    onCompletion: () -> Unit
){

    var step by remember { mutableIntStateOf(0) }


    LaunchedEffect(Unit) {
        step = 1
    }


    Column( modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .safeContentPadding()
        .padding(start = 20.dp, end = 20.dp)
    ) {


        Text(
            text = "Profile Creation",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(13.dp))

        Text(
            text = "STEP 1 OF 4",
            fontSize = 15.sp,
            color = Color.Gray
        )

        StepProgressBar(step)

        Spacer(modifier = Modifier.height(23.dp))

        StepOneScreen()
    }
}