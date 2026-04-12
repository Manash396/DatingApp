package com.mk.datingapp.ui.profile.component

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.datingapp.ui.theme.labelColor
import com.mk.datingapp.ui.theme.placeholderColor
import com.mk.datingapp.ui.theme.textFieldContColor

@Composable
fun StepOneScreen(){

    var fullName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Woman") }
    var expanded by remember { mutableStateOf(false) }

    val genderOptions = listOf("Man", "Woman", "Other")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {


        // 🔹 Section Title
        Text(
            text = "THE BASICS",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Full Name
        Text(
            text = "FULL NAME",
            color = labelColor,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { it.also { fullName = it } },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                cursorColor = labelColor,
                focusedTextColor = labelColor,
                unfocusedTextColor = placeholderColor,
                unfocusedContainerColor = textFieldContColor,
                focusedContainerColor = textFieldContColor,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Age + Gender Row
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // AGE
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = "AGE",
                    color = labelColor,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.colors(
                        cursorColor = labelColor,
                        focusedTextColor = labelColor,
                        unfocusedTextColor = placeholderColor,
                        unfocusedContainerColor = textFieldContColor,
                        focusedContainerColor = textFieldContColor,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    )
                )
            }

            // GENDER
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = "GENDER",
                     color = labelColor,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Box {

                    OutlinedTextField(
                        value = gender,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true },
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            cursorColor = labelColor,
                            focusedTextColor = labelColor,
                            unfocusedTextColor = placeholderColor,
                            unfocusedContainerColor = textFieldContColor,
                            focusedContainerColor = textFieldContColor,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                        )
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        genderOptions.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    gender = it
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StepOneScreenPreview(){
    StepOneScreen()
}