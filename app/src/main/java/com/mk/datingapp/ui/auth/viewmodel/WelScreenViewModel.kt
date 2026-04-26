package com.mk.datingapp.ui.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.mk.datingapp.domain.repository.AnalyticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelScreenViewModel @Inject constructor(
    private val repo : AnalyticsRepository
) : ViewModel() {

    private var isTracked = false

    fun trackScreenOnce(){
        if (!isTracked){
            repo.trackScreen("WelcomeScreen")
            isTracked = true
        }
    }

    fun trackButtonClick(name  : String){
        repo.trackButtonClick(name)
    }
}