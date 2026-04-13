package com.mk.datingapp.ui.auth.screen

import android.util.Log
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.mk.datingapp.ui.theme.headerTextBgColor
import com.mk.datingapp.ui.theme.headerTextColor
import com.mk.datingapp.ui.theme.labelColor
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.compose.LottieAnimatable
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mk.datingapp.R
import com.mk.datingapp.ui.auth.component.AppPasswordField
import com.mk.datingapp.ui.auth.component.AppTextField
import com.mk.datingapp.ui.auth.component.GradientButton
import com.mk.datingapp.ui.auth.component.SocialButton
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onGoogleClick:() -> Unit,
    navController: NavController
) {


    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.logo_anim)
    )


    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isSuccess by rememberSaveable { mutableStateOf(false) }
    var triggerLogin by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(triggerLogin) {
        if(triggerLogin){
            isLoading = true
            delay(2000)
            isLoading = false
            isSuccess = true
            triggerLogin = false
        }
    }


    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            onLoginClick()
            isSuccess = false
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .safeContentPadding()
            .verticalScroll(rememberScrollState())
            .imePadding() // avoid keyboard overlap
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "WelCome Back",
            style = TextStyle(
                color = labelColor,
                fontSize = 17.sp,
                shadow = Shadow(
                    color = headerTextBgColor,
                    offset = Offset(0f, 4f),
                    blurRadius = 12f
                )
            ),
            modifier = Modifier
                .background(
                    color = headerTextBgColor,
                    shape = RoundedCornerShape(13.dp)
                )
                .padding(horizontal = 13.dp, vertical = 7.dp)

        )


        Spacer(modifier = Modifier.height(15.dp))

        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(88.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Log In",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        )

        Text(
            text = "Enter your details to continue your journey.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(29.dp))

        Text(
            "Email Address", style = MaterialTheme.typography.labelMedium,
            color = labelColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        AppTextField(
            value = email,
            onValueChange = { email = it.trim() },
            placeholder = "name@aura.com",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            isError = emailError,
            resetErrorMessage = { emailError = false }
        )


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text("Password", style = MaterialTheme.typography.labelMedium, color = labelColor)
            Text(
                text = "Forgot Password?",
                color = labelColor,
                style = MaterialTheme.typography.labelMedium,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        AppPasswordField(
            value = password,
            onValueChange = { password = it.trim() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            isError = passwordError,
            resetErrorMessage = { passwordError = false }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            GradientButton(
                text = "Login",
                onClick = {
                    emailError = email.isBlank()
                    passwordError = password.isBlank()

                    val hasError = emailError || passwordError
                    if (!hasError) triggerLogin = true
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Divider Text
        Text(
            text = "OR CONTINUE WITH",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        )
        {
            SocialButton(
                text = "Google",
                icon = R.drawable.google_icon,
                onClick = { onGoogleClick() }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Sign Up
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Don't have an account? ",
                color = labelColor,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Sign Up",
                color = labelColor,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.clickable {
                    navController.navigate("signup") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
    }

// loader
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.CircularProgressIndicator(
                    color = labelColor
                )
            }
        }

    }

    Log.d("NAV", navController.currentDestination?.route ?: "null")

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen({},
        onGoogleClick = {},
        navController = rememberNavController()
    )
}