package com.mk.datingapp.ui.profile.creation

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileCreationViewModel @Inject constructor(

) : ViewModel(){

    private val _uiState = MutableStateFlow(ProfileCreationState())
    val uiState : StateFlow<ProfileCreationState> = _uiState

    fun nextStep(){
        _uiState.update {
            it.copy(step = it.step+1)
        }
    }

    fun prevStep(){
        _uiState.update {
            it.copy(step = it.step-1)
        }
    }

    fun updateState(newState: ProfileCreationState) {
        _uiState.value = newState
    }



    fun setImages(images: List<Uri>) {
        _uiState.update { it.copy(images = images) }
    }


}