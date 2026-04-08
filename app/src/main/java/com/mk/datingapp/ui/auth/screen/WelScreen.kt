package com.mk.datingapp.ui.auth.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mk.datingapp.R
import com.mk.datingapp.ui.auth.component.GradientButton
import com.mk.datingapp.ui.auth.component.OutlineButton
import com.mk.datingapp.ui.auth.component.SocialButton

@Composable
fun WelScreen(
    onGetStarted: () -> Unit,
    onGoogleClick: () -> Unit,
    onEmailClick: () -> Unit,
    navController: NavController
){


    Box(modifier = Modifier.fillMaxSize()) {

        // Background Image
        Image(
            painter = painterResource(id = R.drawable.bg_wel),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // Gradient Overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 1f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Ethereal",
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    color = Color.Black
                )

                Text(
                    text = "Login",
                    color = Color.Black,
                    fontSize = 17.sp,
                    modifier = Modifier.clickable {
                        navController.navigate("login")
                        {
                            // keeping the wel screen (start destination of a nested graph)
                            popUpTo("wel") { inclusive = false }
                        }
                    }
                )
            }

            // Center Text
            Column {
                Text(
                    text = "Connections",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "In Bloom",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF9C27B0)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Experience the art of intentional dating within a curated, elevated space.",
                    color = Color.LightGray
                )
            }

            // Bottom Buttons
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                GradientButton(
                    text = "Get Started",
                    onClick = onGetStarted
                )

                SocialButton(
                    text = "Continue with Google",
                    icon = R.drawable.google_icon,
                    onClick = onGoogleClick
                )

                OutlineButton(
                    text = "Sign up with Email",
                    onClick = onEmailClick
                )

                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "By continuing, you agree to our Terms of Service and Privacy Policy.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }

}





@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelScreenPreview() {
    WelScreen(
        onGetStarted = {},
        onGoogleClick = {},
        onEmailClick = {},
        navController = rememberNavController()
    )
}