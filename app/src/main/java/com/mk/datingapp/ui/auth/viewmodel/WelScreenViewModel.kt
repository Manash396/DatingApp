package com.mk.datingapp.ui.auth.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.datingapp.domain.repository.AnalyticsRepository
import com.mk.datingapp.domain.repository.AuthRepository
import com.mk.datingapp.ui.auth.event.WelScreenEvent
import com.mk.datingapp.ui.auth.state.WelUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelScreenViewModel @Inject constructor(
    private val analyticsRepository: AnalyticsRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var isTracked = false

    private val _state = MutableStateFlow(WelUiState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<WelScreenEvent>()
    val event = _event.asSharedFlow()

    fun trackScreenOnce(){
        if (!isTracked){
            analyticsRepository.trackScreen("WelcomeScreen")
            isTracked = true
        }
    }

    fun trackButtonClick(name  : String){
        analyticsRepository.trackButtonClick(name)
    }

    fun signInWithGoogle(idToken: String){
        viewModelScope.launch {

            _state.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            val result = authRepository.signInWithGoogle(idToken)

            result.onSuccess { user ->

                val isNewUser  = authRepository.checkAndCreateUser(user.uid , user.email, user.name)

                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
                Log.d("Mkdev","NewUser uuid ${user.uid}")

                if (isNewUser) {
                    _event.emit(WelScreenEvent.OnNavigateToProfile)
                } else {
                    _event.emit(WelScreenEvent.OnNavigateToMain)
                }

            }

            result.onFailure { e ->

                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }

            }
        }
    }
}