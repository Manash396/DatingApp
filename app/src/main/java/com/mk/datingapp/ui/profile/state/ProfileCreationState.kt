package com.mk.datingapp.ui.profile.state

import android.net.Uri

data class ProfileCreationState(
    val step: Int = 2,
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
    val selectedImages: List<Uri> = emptyList(),

    // Step 3
    val name: String = "",

    // Step 4
    val bio: String = ""
)
