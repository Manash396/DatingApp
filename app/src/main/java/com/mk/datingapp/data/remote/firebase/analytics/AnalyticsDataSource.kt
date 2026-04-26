package com.mk.datingapp.data.remote.firebase.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class
AnalyticsDataSource @Inject constructor(
    private val firebaseAnalytics : FirebaseAnalytics
) {

    fun trackScreen(screenName : String){
        val bundle  = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }


    fun trackEvent(eventName :  String, params : Bundle ?= null){
        firebaseAnalytics.logEvent(eventName, params)
    }

    fun trackButton(buttonName: String) {
        val bundle  = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_NAME, buttonName)
        }
        firebaseAnalytics.logEvent("button_click", bundle)
    }

}