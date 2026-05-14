package com.mk.datingapp.ui.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.datingapp.data.local.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    val isLoggedIn = userPreferences.isLoggedIn
        .stateIn(
            viewModelScope,
            SharingStarted.Companion.WhileSubscribed(5000),
            false
        )


}