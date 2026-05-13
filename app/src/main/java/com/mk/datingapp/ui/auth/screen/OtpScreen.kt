package com.mk.datingapp.ui.auth.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mk.datingapp.ui.auth.component.GradientButton
import com.mk.datingapp.ui.theme.labelColor
import kotlinx.coroutines.delay
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun OtpScreen(
    onVerified: () -> Unit,
    navController: NavController
) {

    var otpValues = remember { List(6) { mutableStateOf("") } }

    var timeLeft by remember { mutableIntStateOf(60) }

    var formattedTime by remember { mutableStateOf("1:33") }

    val focusRequester =  List(6) { FocusRequester() }
    var backTrigger  by remember { mutableIntStateOf(2) }

    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isSuccess by rememberSaveable { mutableStateOf(false) }
    var triggerVerifi by rememberSaveable { mutableStateOf(false) }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorText by rememberSaveable{ mutableStateOf("Fill in all the boxes")}

    val haptic = LocalHapticFeedback.current


    LaunchedEffect(triggerVerifi) {
        if (triggerVerifi){
            isLoading = true
            delay(3000)
            isLoading = false
            isSuccess = true
            triggerVerifi = false
        }
    }

    LaunchedEffect(showError) {
        if(showError){
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            delay(2000)
            showError = false
        }
    }


    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            onVerified()
            isSuccess = false
        }
    }

    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft>0){
            delay(1000)
            timeLeft--
            val minutes = timeLeft / 60
            val seconds = timeLeft % 60

            formattedTime = String.format(Locale.ENGLISH,"%02d:%02d", minutes, seconds)
        }
    }

    LaunchedEffect(Unit) {
        focusRequester[0].requestFocus()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F0F1A),
                        Color(0xFF1A0B2E)
                    )
                )
            )
            .safeContentPadding()
            .imePadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Image(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentScale = ContentScale.Inside,
                        contentDescription = "back",
                        colorFilter = ColorFilter.tint(labelColor),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Verification",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = labelColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(end = 60.dp)
                )

            }

            Spacer(modifier = Modifier.height(50.dp))

            // 🔹 Top Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(start = 16.dp , end = 16.dp)
            ) {

                Text(
                    text = "Verify Your Soul",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "A 6-digit code has been sent to your registered email.",
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(60.dp))


                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    otpValues.forEachIndexed { index, state ->
                        OutlinedTextField(
                            value = state.value,
                            onValueChange = { newValue ->

                                if (newValue.isEmpty() && state.value.isNotEmpty()){
                                    backTrigger--
                                }

                                if (newValue.length <= 1 && newValue.isDigitsOnly()) {
                                    state.value = newValue

                                    // move to the next field
                                    if (index<5 && newValue.isNotEmpty()){
                                        focusRequester[index+1].requestFocus()
                                    }

                                }
                                //
                            },
                            modifier = Modifier
                                .size(width = 47.dp, height = 56.dp)
                                .focusRequester(focusRequester[index])
                                .onKeyEvent { event ->
                                    if (event.key == Key.Backspace && event.type == KeyEventType.KeyUp && index > 0) {
                                        if (backTrigger == 0) {
                                            focusRequester[index - 1].requestFocus()
                                            backTrigger = 2
                                        } else {
                                            backTrigger--
                                        }
                                    }
                                    false
                                }
                            ,
                            textStyle = androidx.compose.ui.text.TextStyle(
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )
                    }
                }

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedVisibility(
                        visible = showError,
                        enter = fadeIn() + slideInVertically(initialOffsetY = { -20 }),
                        exit = fadeOut() + slideOutVertically(targetOffsetY = { -20 })
                    ) {
                        Text(
                            text = "Fill in all the boxes",
                            color = Color.Red,
                            fontSize = 12.sp,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                // 🔹 Timer
                Text(
                    text = formattedTime,
                    color = Color(0xFFBB86FC),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "RESEND CODE",
                    color = if (timeLeft == 0) Color.White else Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .clickable(enabled = (timeLeft == 0)){
                            timeLeft = 60
                        }
                )
            }


            Spacer(modifier = Modifier.height(40.dp))


            Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                    .padding(start = 16.dp , end = 16.dp)
            ) {

                GradientButton(
                    text  = "Verify",
                    onClick = {
                        val otpValues  = otpValues.joinToString(""){ it.value }

                        if(otpValues.length <6){
                            showError = true
                        }else{
                            showError = false
                            triggerVerifi = true
                        }
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Having trouble? ",
                        color = Color.Gray,
                        fontSize = 12.sp,
                    )

                    Text(
                        text = "Contact MkDev",
                        color = labelColor,
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic
                    )

                }

            }

        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0F0F1A).copy(alpha = 0.5f),
                            Color(0xFF1A0B2E).copy(alpha = 0.5f)
                        )
                    )
                ),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.CircularProgressIndicator(
                    color = labelColor
                )
            }
        }
    }

}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun OtpScreenPreview() {
    OtpScreen({} ,
        navController = rememberNavController()
    )
}