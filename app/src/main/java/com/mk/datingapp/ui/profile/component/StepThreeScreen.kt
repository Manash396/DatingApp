package com.mk.datingapp.ui.profile.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mk.datingapp.ui.auth.component.GradientButton
import com.mk.datingapp.ui.profile.state.ProfileCreationState
import com.mk.datingapp.utils.AppConstant.categories

@Composable
fun StepThreeScreen(
    currentState: ProfileCreationState,
    onStateChanged: (ProfileCreationState) -> Unit,
    onPrevStep : () -> Unit,
    onNextStep: () -> Unit
) {

    val context = LocalContext.current

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
                    text = "Define your essence",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            categories.forEach { (title, items) ->
                item {
                    Text(
                        text = title,
                        color = Color(0xFFB388FF),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                item {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        items.forEach { tag ->
                            val isSelected  = currentState.selectedTags.contains(tag)

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(
                                        if (isSelected) Color(0xFFB388FF) else Color.DarkGray
                                    )
                                    .clickable{
                                        val updated = if (isSelected) {
                                            currentState.selectedTags - tag
                                        } else {
                                            currentState.selectedTags + tag
                                        }
                                        onStateChanged(currentState.copy(selectedTags = updated))
                                    }
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                            ){
                                Text(
                                    text = tag,
                                    color = if (isSelected) Color.Black else Color.White,
                                    fontSize = 12.sp
                                )
                            }

                        }

                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1A1A1A))
                        .padding(12.dp)
                ) {
                    Column {
                        Text("PRO TIP", color = Color.Gray, fontSize = 10.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Vibe Consistency", color = Color.White, fontWeight = FontWeight.Bold)
                        Text(
                            "Profiles with 5+ shared interests result in 40% more meaningful conversations.",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
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
                modifier = Modifier.size(24.dp)
                    .clickable{
                        onPrevStep()
                    }
            )

            Spacer(modifier = Modifier.width(33.dp))

            GradientButton(
                text = "Continue",
                onClick = {
                    val isEmpty = currentState.selectedTags.size < 5
                    if (isEmpty) {
                        Toast.makeText(
                            context,
                            "At least select five interest",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        onNextStep()
                    }
                }
            )
        }


    }

}