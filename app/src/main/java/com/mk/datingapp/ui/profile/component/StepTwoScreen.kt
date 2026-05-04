package com.mk.datingapp.ui.profile.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.mk.datingapp.R
import com.mk.datingapp.ui.auth.component.GradientButton
import com.mk.datingapp.ui.profile.state.ProfileCreationState
import com.mk.datingapp.ui.theme.labelColor

@Composable
fun StepTwoScreen(
    currentState: ProfileCreationState,
    onStateChanged: (ProfileCreationState) -> Unit,
    onPrevStep: () -> Unit,
    onNextStep: () -> Unit
) {

    var currentImageIndex by rememberSaveable { mutableIntStateOf(0) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(2)
    ) { uris ->
        if (uris.isNotEmpty()) {
            val images  = currentState.images.toMutableList()
            images[currentImageIndex] = uris[0]

            onStateChanged(
                currentState.copy(
                    images = images
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.Black)
    ) {

        //  Title
        Text(
            text = "Curate your gallery",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Select your best angles to capture the essence of your story.",
            color = Color.Gray,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        //  TOP SECTION
        Row(
            modifier = Modifier.fillMaxWidth()
                .aspectRatio(1f)
            ,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            //  BIG PRIMARY CARD
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.DarkGray)
                    .clickable {
                        currentImageIndex = 0
                        launcher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                contentAlignment = Alignment.Center
            ) {

                if (currentState.images[0] != null) {
                    Image(
                        painter = rememberAsyncImagePainter(currentState.images[0]),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painter = painterResource(R.drawable.baseline_add_photo_alternate_24), null, tint = labelColor)
                        Text("Primary Portrait", color = Color.White, fontSize = 12.sp)
                        Text("Click to upload", color = Color.Gray, fontSize = 10.sp)
                    }
                }
            }

            //  RIGHT STACK
            Column(
                modifier = Modifier.weight(1f)
                    .fillMaxHeight()
                ,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                repeat(2) { index ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Black)
                            .drawBehind{
                                val strokeWidth = 2.dp.toPx()
                                val dashWidth = 5.dp.toPx()
                                val gapWidth = 3.dp.toPx()

                                drawRoundRect(
                                    color = Color.Gray,
                                    size = size,
                                    cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx()),
                                    style = Stroke(
                                        width = strokeWidth,
                                        pathEffect = PathEffect.dashPathEffect(
                                            floatArrayOf(dashWidth, gapWidth)
                                        )
                                    )
                                )
                            }
                            .clickable {
                                currentImageIndex = 1+index
                                launcher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        if (currentState.images[1+index] != null){
                            Image(
                                painter = rememberAsyncImagePainter(currentState.images[1+index
                                ]),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                                Icon(Icons.Default.Add, null, tint = Color.White)
                            }

                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        //  BOTTOM SMALL GRID
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black)
                        .drawBehind{
                            val strokeWidth = 2.dp.toPx()
                            val dashWidth = 5.dp.toPx()
                            val gapWidth = 3.dp.toPx()

                            drawRoundRect(
                                color = Color.Gray,
                                size = size,
                                cornerRadius = CornerRadius(12.dp.toPx(), 12.dp.toPx()),
                                style = Stroke(
                                    width = strokeWidth,
                                    pathEffect = PathEffect.dashPathEffect(
                                        floatArrayOf(dashWidth, gapWidth)
                                    )
                                )
                            )
                        }
                        .clickable {
                            currentImageIndex = 3+index
                            launcher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (currentState.images[3+index] != null){
                        Image(
                            painter = rememberAsyncImagePainter(currentState.images[3+index]),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Icon(Icons.Default.Add, null, tint = Color.White)
                    }

                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(18.dp))

        //  TIP CARD
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF1A1A1A))
                .padding(12.dp)
        ) {
            Column {
                Text("💡 Curator's Tip", color = Color.White, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "High-resolution images with natural lighting tend to foster deeper connections.",
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }
        }



        Spacer(modifier = Modifier.height(18.dp))

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
                modifier = Modifier.size(24.dp)
                    .clickable{
                        onPrevStep()
                    }
            )

            Spacer(modifier = Modifier.width(33.dp))

            GradientButton(
                text = "Continue",
                onClick = {

                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StepTwoScreenPreview() {

    val dummyState = ProfileCreationState(
        step = 2,
        images = emptyList(),
        selectedImages = emptyList()
    )

    StepTwoScreen(
        currentState = dummyState,
        onStateChanged = {},
        onPrevStep = {},
        onNextStep = {}
    )
}