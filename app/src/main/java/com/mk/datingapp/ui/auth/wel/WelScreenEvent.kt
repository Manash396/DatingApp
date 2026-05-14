package com.mk.datingapp.ui.auth.wel

sealed class WelScreenEvent {
    object OnLoginClick : WelScreenEvent()
    object OnEmailClick : WelScreenEvent()
    object OnGetStartedClick : WelScreenEvent()
    object onGoogleSuccess : WelScreenEvent()

    object OnNavigateToProfile : WelScreenEvent()
    object OnNavigateToMain : WelScreenEvent()
}