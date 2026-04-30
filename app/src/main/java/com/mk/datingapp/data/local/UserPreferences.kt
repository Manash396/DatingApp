package com.mk.datingapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(
    private val context : Context
) {

    companion object{
        val UID = stringPreferencesKey("uid")
        val NAME = stringPreferencesKey("name")
        val EMAIL = stringPreferencesKey("email")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val IS_PROFILE_CREATED = booleanPreferencesKey("is_profile_created")
    }

    suspend fun saveUser(uid: String, name: String, email: String) {
        context.dataStore.edit { prefs ->
            prefs[UID] = uid
            prefs[NAME] = name
            prefs[EMAIL] = email
            prefs[IS_LOGGED_IN] = true
        }
    }



    val uid : Flow<String?> = context.dataStore.data.map {
        it[UID]
    }

    val name : Flow<String?> = context.dataStore.data.map {
        it[NAME]
    }

    val email : Flow<String?> = context.dataStore.data.map {
        it[EMAIL]
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map {
        it[IS_LOGGED_IN] ?: false
    }

    suspend fun logout() {
        context.dataStore.edit { it.clear() }
    }

}