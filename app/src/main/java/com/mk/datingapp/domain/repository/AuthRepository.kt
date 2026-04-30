package com.mk.datingapp.domain.repository

import android.content.Context
import com.mk.datingapp.domain.model.AuthUser

interface AuthRepository {

    suspend fun signInWithGoogle(
        idToken : String
    ) : Result<AuthUser>


    suspend fun checkAndCreateUser(
        uid: String,
        email: String?,
        name: String?
    ): Boolean


}