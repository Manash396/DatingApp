package com.mk.datingapp.ui.auth.state

data class WelUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isNewUser: Boolean? = null
)
