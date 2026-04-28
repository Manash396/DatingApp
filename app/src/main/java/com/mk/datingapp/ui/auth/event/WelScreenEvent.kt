package com.mk.datingapp.ui.auth.event

sealed class WelScreenEvent {
    object OnLoginClick : WelScreenEvent()
    object OnGoogleClick : WelScreenEvent()
    object OnEmailClick : WelScreenEvent()
    object OnGetStartedClick : WelScreenEvent()
}
