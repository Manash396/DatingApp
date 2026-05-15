package com.mk.datingapp.ui.profile.component


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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.datingapp.utils.component.AppTextField
import com.mk.datingapp.utils.component.GradientButton
import com.mk.datingapp.ui.profile.creation.ProfileCreationState
import com.mk.datingapp.ui.theme.labelColor
import com.mk.datingapp.ui.theme.placeholderColor
import com.mk.datingapp.ui.theme.textFieldContColor
import com.mk.datingapp.utils.Util.getUserLocation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepOneScreen(
    currentState : ProfileCreationState,
    onStateChanged : (ProfileCreationState) -> Unit,
    nextStep : () -> Unit
) {

    val context = LocalContext.current

    // ---------------- STATE ----------------

    var expanded by remember { mutableStateOf(false) }
    val genderOptions = listOf("Male", "Female", "Other")



    // ---------------- ERROR STATE ----------------
    var fullNameError by remember { mutableStateOf(false) }
    var userNameError by remember { mutableStateOf(false) }
    var ageError by remember { mutableStateOf(false) }
    var presenceError by remember { mutableStateOf(false) }
    var genderError by remember { mutableStateOf(false) }
    var professionError by remember { mutableStateOf(false) }

//    -------------- Location ------------------------
    var requestLocation by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }



    if (requestLocation) {
        Log.d("KRISHNA", "enterres the requestLocation ")
        LocationPermissionHandler(
            context,
            onGranted = {
                getUserLocation(context) {
                    onStateChanged(currentState.copy(location = it))
                    requestLocation = false
                }
            },
            onNotGranted = {
                requestLocation = false
            }
        )
    }

    LaunchedEffect(requestLocation) {
        if (requestLocation){
            isLoading = true
        }else{
            isLoading = false
        }
    }


    // ---------------- UI ----------------

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 13.dp)
        ) {

            Text(
                text = "The Basics",
                color = Color.White,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // -------- FULL NAME --------
            Text("FULL NAME", color = labelColor, fontSize = 12.sp)

            Spacer(modifier = Modifier.height(10.dp))

            AppTextField(
                value = currentState.fullName,
                onValueChange = {
                    onStateChanged(currentState.copy(fullName = it))
                    fullNameError = false
                },
                placeholder = "Enter name",
                isError = fullNameError,
                errorText = "Name required",
                resetErrorMessage = { fullNameError = false }
            )


            Text("USERNAME", color = labelColor, fontSize = 12.sp)

            Spacer(modifier = Modifier.height(10.dp))

            AppTextField(
                value = currentState.userName,
                onValueChange = {
                    onStateChanged(currentState.copy(userName = it))
                    userNameError = false
                },
                placeholder = "Enter username",
                isError = userNameError,
                errorText = "Name required",
                resetErrorMessage = { userNameError = false }
            )

            Text("PROFESSION", color = labelColor, fontSize = 12.sp)

            Spacer(modifier = Modifier.height(10.dp))

            AppTextField(
                value = currentState.profession,
                onValueChange = {
                    onStateChanged(currentState.copy(profession = it))
                    professionError = false
                },
                placeholder = "Enter profession",
                isError = professionError,
                errorText = "Fiels required",
                resetErrorMessage = { professionError = false }
            )

//            Spacer(modifier = Modifier.height(16.dp))

            // -------- AGE + GENDER --------
            Row {
                Column(modifier = Modifier.weight(1f)) {

                    Text("AGE", color = labelColor, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(10.dp))

                    AppTextField(
                        value = currentState.age,
                        onValueChange = {
                            onStateChanged(currentState.copy(age = it))
                            ageError = false
                        },
                        placeholder = "e.g. 21",
                        isError = ageError,
                        errorText = "Age required",
                        resetErrorMessage = { ageError = false }
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {

                    Text("GENDER", color = labelColor, fontSize = 12.sp)

                    Spacer(modifier = Modifier.height(10.dp))

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(58.dp)
                                .background(textFieldContColor, shape = RoundedCornerShape(20.dp))
                                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
                                .clickable {
                                    expanded = true
                                },
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 15.dp, end = 7.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = currentState.gender,
                                    color = labelColor
                                )

                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Dropdown",
                                    tint = labelColor
                                )
                            }
                        }

//                    Spacer(modifier = Modifier.height(30.dp))

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            genderOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        onStateChanged(currentState.copy(gender = option))
                                        expanded = false
                                    }
                                )
                            }
                        }

                    }
                }
            }

//            Spacer(modifier = Modifier.height(16.dp))

            // -------- PRESENCE --------
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "PRESENCE",
                    color = labelColor,
                    fontSize = 12.sp
                )

                Text(
                    text = "Auto Detect",
                    color = labelColor,
                    fontSize = 12.sp,
                    modifier = Modifier.clickable {
                        // trigger location logic
                        requestLocation = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            AppTextField(
                value = currentState.location,
                onValueChange = {
                    onStateChanged(currentState.copy(location = it))
                    presenceError = false
                },
                placeholder = "Your location",
                isError = presenceError,
                errorText = "Location required",
                resetErrorMessage = { presenceError = false }
            )



//            Spacer(modifier = Modifier.height(16.dp))

            // -------- BIO --------
            Text("YOUR STORY", color = labelColor, fontSize = 12.sp)

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = currentState.about,
                onValueChange = { onStateChanged(currentState.copy(about = it)) },
                placeholder = { Text("Tell us about yourself...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = textFieldContColor,
                    unfocusedContainerColor = textFieldContColor,
                    cursorColor = labelColor,
                    focusedTextColor = labelColor,
                    unfocusedTextColor = placeholderColor,
                    focusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(20.dp)
            )

            Spacer(modifier = Modifier.height(44.dp))

            // -------- BUTTON --------
                GradientButton(
                    text = "Continue to Photos >>",
                    onClick = {

                        // VALIDATION
                        with(currentState) {
                            fullNameError = fullName.isBlank()
                            ageError = age.isBlank()
                            presenceError = location.isBlank()
                            genderError = gender.isBlank()
                            userNameError = userName.isBlank()
                        }

                        val hasError = fullNameError || ageError || presenceError || genderError || userNameError

                        if (!hasError) {
                            nextStep()
                        }
                    }
                )
        }

        // loader
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StepOneScreenPreview() {
    StepOneScreen(currentState = ProfileCreationState() , {} ,{})
}