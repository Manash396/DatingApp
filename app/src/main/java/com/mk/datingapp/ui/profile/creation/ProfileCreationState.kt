package com.mk.datingapp.ui.profile.creation

import android.net.Uri

data class ProfileCreationState(
    val step: Int = 4,
    val isLoading :Boolean = false,
    val error : String? = null,

    // Step 1
    val fullName : String = "",
    val userName : String = "",
    val age : String = "",
    val gender : String = "",
    val location : String = "",
    val about : String = "",


    // Step 2 (Gallery)
    val images: List<Uri?> = List(6){null},
//    val selectedImages: List<Uri> = emptyList(),

    // Step 3
    val selectedTags: List<String> = emptyList(),

    // Step 4
    val answers: Map<String, String> = emptyMap()
)

data class Question(
    val title: String,
    val options: List<String>
)
