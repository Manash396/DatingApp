package com.mk.datingapp.data.repository

import android.content.Context
import com.mk.datingapp.data.remote.firebase.auth.AuthRemoteDataSource
import com.mk.datingapp.data.remote.firebase.firestore.UserRemoteDataSource
import com.mk.datingapp.domain.model.AuthUser
import com.mk.datingapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authSource : AuthRemoteDataSource,
    private val userSource : UserRemoteDataSource
) : AuthRepository{

    override suspend fun signInWithGoogle(idToken : String ): Result<AuthUser> {
       return authSource.signInWithGoogle(idToken)
    }

    override suspend fun checkAndCreateUser(
        uid: String,
        email: String?,
        name: String?
    ): Boolean {
        return userSource.checkAndCreateUser(uid,email,name)
    }
}