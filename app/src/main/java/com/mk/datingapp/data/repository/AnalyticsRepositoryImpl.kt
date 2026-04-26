package com.mk.datingapp.data.repository

import android.os.Bundle
import com.mk.datingapp.data.remote.firebase.analytics.AnalyticsDataSource
import com.mk.datingapp.domain.repository.AnalyticsRepository
import javax.inject.Inject

class AnalyticsRepositoryImpl @Inject constructor(
private val dataSource: AnalyticsDataSource
) : AnalyticsRepository{

    override fun trackScreen(name : String) {
        dataSource.trackScreen(name)
    }

    override fun trackButtonClick(name: String) {
        dataSource.trackButton(name)
    }

    override fun trackEvent(name: String , message : String) {
        val bundle  = Bundle().apply {
            putString("event_message",message)
        }
        dataSource.trackEvent(name, bundle)
    }

}