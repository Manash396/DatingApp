package com.mk.datingapp.ui.profile.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.mk.datingapp.ui.profile.state.ProfileCreationState
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

    fun toggleImage(uri : Uri){
        _uiState.update{ state ->
            val updated  = if (state.selectedImages.contains(uri)){
                state.selectedImages - uri
            }else{
                if (state.selectedImages.size < 5)
                    state.selectedImages + uri
                else state.selectedImages
            }
            state.copy(selectedImages = updated)
        }
    }

    fun setImages(images: List<Uri>) {
        _uiState.update { it.copy(images = images) }
    }


}