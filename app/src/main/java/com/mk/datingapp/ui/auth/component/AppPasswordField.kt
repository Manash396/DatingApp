package com.mk.datingapp.ui.auth.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.datingapp.R
import com.mk.datingapp.ui.theme.labelColor
import com.mk.datingapp.ui.theme.placeholderColor
import com.mk.datingapp.ui.theme.textFieldContColor
import kotlinx.coroutines.delay

@Composable
fun AppPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier? = null,
    isError : Boolean ,
    errorText : String = "",
    resetErrorMessage: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    var errorShow by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isError) {
        if (isError) {
            errorShow = true

            delay(3000)
            errorShow = false
            resetErrorMessage()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = "*****", color = placeholderColor) },
            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Icon(
                    imageVector = if (visible)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff,
                    contentDescription = "Toggle Password",
                    modifier = Modifier.clickable {
                        visible = !visible
                    }
                )
            },
            shape = RoundedCornerShape(20.dp),
            modifier = modifier ?: Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = textFieldContColor,
                focusedContainerColor = textFieldContColor,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = labelColor,
                focusedTextColor = labelColor,
                unfocusedTextColor = placeholderColor
            ),
            singleLine = true
        )

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .padding(start = 20.dp, end =  20.dp)
            ,
        ) {
            AnimatedVisibility(
                visible = errorShow,
                enter = fadeIn() + slideInVertically(initialOffsetY = { -20 }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { -20 })
            ) {
                Text(
                    text = errorText.ifEmpty { "Field is required" },
                    color = Color.Red,
                    fontSize = 12.sp,
                )
            }
        }

    }
}