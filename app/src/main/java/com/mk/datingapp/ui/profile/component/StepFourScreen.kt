package com.mk.datingapp.ui.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.datingapp.utils.component.GradientButton
import com.mk.datingapp.ui.profile.creation.ProfileCreationState
import com.mk.datingapp.utils.AppConstant.questions

@Composable
fun StepFourScreen(
    currentState: ProfileCreationState,
    onStateChanged: (ProfileCreationState) -> Unit,
    onPrevStep : () -> Unit,
    onCompletion: () -> Unit
) {

    val context = LocalContext.current

    var currentIndex by remember { mutableIntStateOf(0) }

    val currentQuestion = questions[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(bottom = 13.dp)
    ) {

        LazyColumn(
            Modifier.weight(1f), verticalArrangement =  Arrangement.spacedBy(16.dp)
        ) {

            item{
                Text(
                    text = "Personality Questions",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    Text(
                        text = "\"${currentQuestion.title}\"",
                        color = Color.LightGray,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    currentQuestion.options.forEach { option ->
                        val isSelected  = currentState.answers[currentQuestion.title] == option

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (isSelected) Color(0xFF2A1E3F).copy(alpha = 0.5f) else Color(0xFF1A1A1A)
                                )
                                .border(
                                    width = 1.5.dp,
                                    color = if (isSelected) Color(0xFF2A1E3F) else Color(0xFF1A1A1A),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable {
                                    val updated = currentState.answers.toMutableMap()
                                    updated[currentQuestion.title] = option
                                    onStateChanged(currentState.copy(answers = updated))
                                }
                                .padding(16.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Box(
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, Color.Gray, CircleShape)
                                        .background(
                                            if (isSelected) Color(0xFFB388FF) else Color.Transparent
                                        )
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = option,
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            }

                        }
                    }
                }

            }

            item{
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    if (currentIndex > 0) {

                        TextButton(
                            onClick = {
                                currentIndex--
                            }
                        ) {
                            Text(
                                "Prev",
                                fontSize = 15.sp,
                                fontStyle = FontStyle.Italic,
                                textDecoration = TextDecoration.Underline
                            )
                        }

                    }

                    Spacer(modifier = Modifier.weight(1f))

                    if (currentIndex < questions.size-1) {
                        TextButton(
                            onClick = {
                                currentIndex++
                            }
                        ) {
                            Text(
                                "Next",
                                fontSize = 15.sp,
                                fontStyle = FontStyle.Italic,
                                textDecoration = TextDecoration.Underline
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier  = Modifier.height(20.dp))

        // navigation
        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically,
        ){


            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White.copy(alpha = 0.8f),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onPrevStep()
                    }
            )

            Spacer(modifier = Modifier.width(33.dp))

            GradientButton(
                text = "Complete",
                onClick = {
                    onCompletion()
                },

                enabled = currentState.answers.size == questions.size

            )
        }


    }

}