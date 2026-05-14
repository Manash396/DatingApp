package com.mk.datingapp.ui.auth.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mk.datingapp.utils.component.AppPasswordField
import com.mk.datingapp.utils.component.AppTextField
import com.mk.datingapp.utils.component.GradientButton
import com.mk.datingapp.ui.theme.labelColor
import kotlinx.coroutines.delay

@Composable
fun RegisterScreen(
    onRegisterClick: () -> Unit,
    navController: NavController
) {

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }

    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isSuccess by rememberSaveable { mutableStateOf(false) }
    var triggerRegister by rememberSaveable { mutableStateOf(false) }

    var usernameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    var confirmPasswordErrorText by remember { mutableStateOf("") }

    LaunchedEffect(triggerRegister) {
        if (triggerRegister){
            isLoading = true
            delay(3000)
            isLoading = false
            isSuccess = true
            triggerRegister = false
        }
    }

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            onRegisterClick()
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
                .padding()
                .safeContentPadding()
                .verticalScroll(rememberScrollState())
                .imePadding(),
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
                    text = "Create Account",
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

            // now the title of the registration

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Join the",
                    fontWeight = FontWeight.Medium,
                    fontSize = 26.sp,
                    color = Color.White
                )

                Text(
                    text = " Ethereal",
                    fontWeight = FontWeight.Medium,
                    fontSize = 26.sp,
                    color = labelColor,
                    fontStyle = FontStyle.Italic
                )

            }
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Step into a world of intentional connections and refined digital elegance",
                fontWeight = FontWeight.Normal,
                fontSize = 17.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                "UserName", style = MaterialTheme.typography.labelMedium,
                color = labelColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            AppTextField(
                value = username,
                onValueChange = { username = it.trim() },
                placeholder = "Phillips Lackner",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                isError = usernameError ,
                resetErrorMessage={ usernameError = false }
            )



            Text(
                "Email Address", style = MaterialTheme.typography.labelMedium,
                color = labelColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
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
                resetErrorMessage = { emailError =  false}
            )


            Text(
                "Password",
                style = MaterialTheme.typography.labelMedium,
                color = labelColor,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            AppPasswordField(
                value = password,
                onValueChange = { password = it.trim() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                isError = passwordError,
                resetErrorMessage = {passwordError = false}
            )

//            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Confirm Password",
                style = MaterialTheme.typography.labelMedium,
                color = labelColor,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            AppPasswordField(
                value = passwordConfirm ,
                onValueChange = { passwordConfirm  = it.trim() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                isError = confirmPasswordError || !confirmPasswordErrorText.isBlank(),
                errorText = confirmPasswordErrorText,
                resetErrorMessage = {
                    confirmPasswordError = false
                    confirmPasswordErrorText = ""
                }
            )

//            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it }, colors = CheckboxDefaults.colors(
                        checkmarkColor = Color.White,
                        checkedColor = labelColor
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "By continuing, you agree to Aura Amity’s Terms of Service and Privacy Policy.",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)

            ) {
                GradientButton(
                    text = "Sign Up",
                    onClick = {

                        usernameError = username.isBlank()
                        emailError = email.isBlank()
                        passwordError = password.isBlank()
                        confirmPasswordError = passwordConfirm.isBlank()

                        val hasError = usernameError || emailError || passwordError || confirmPasswordError
                        if (password != passwordConfirm){
                            confirmPasswordErrorText = "Password did not matched"
                            return@GradientButton
                        }

                       if(!hasError) triggerRegister = true
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
                CircularProgressIndicator(
                    color = labelColor
                )
            }
        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        {},
        navController = rememberNavController()
    )
}