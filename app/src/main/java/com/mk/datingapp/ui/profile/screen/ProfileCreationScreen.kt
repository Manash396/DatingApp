package com.mk.datingapp.ui.profile.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mk.datingapp.ui.profile.component.StepOneScreen
import com.mk.datingapp.ui.profile.component.StepProgressBar
import com.mk.datingapp.ui.profile.component.StepTwoScreen
import com.mk.datingapp.ui.profile.viewmodel.ProfileCreationViewModel
import com.mk.datingapp.ui.theme.labelColor

@Composable
fun ProfileCreationScreen(
    onCompletion: () -> Unit,
    viewModel : ProfileCreationViewModel = hiltViewModel()
){


    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier =Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
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
                text = "STEP ${state.step} OF 4",
                fontSize = 15.sp,
                color = Color.Gray
            )

            StepProgressBar(state.step)

            Spacer(modifier = Modifier.height(23.dp))

            when (state.step) {
                1 -> {
                    StepOneScreen(
                        currentState = state,
                        onStateChanged = { newState ->
                            viewModel.updateState((newState))
                        },
                        nextStep = {
                            viewModel.nextStep()
                        }
                    )
                }

                2 -> {
                    StepTwoScreen(
                        currentState = state,
                        onStateChanged = { newState ->
                            viewModel.updateState((newState))
                        },
                        onPrevStep = {
                            viewModel.prevStep()
                        },
                        onNextStep = {
                            viewModel.nextStep()
                        }
                    )
                }

                3 -> {

                }

                4 -> {

                }
            }


        }

        // loader
        if (state.isLoading) {
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
fun ProfileScreenCreationPreview() {
    ProfileCreationScreen(
        onCompletion = {}
    )

}