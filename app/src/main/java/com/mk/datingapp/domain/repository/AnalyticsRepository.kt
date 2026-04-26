package com.mk.datingapp.domain.repository

interface AnalyticsRepository {

    fun trackScreen(name: String)

    fun trackButtonClick(name: String)

    fun trackEvent(name: String , message : String)
}