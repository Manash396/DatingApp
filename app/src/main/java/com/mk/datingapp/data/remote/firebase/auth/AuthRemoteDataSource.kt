package com.mk.datingapp.data.remote.firebase.auth

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.mk.datingapp.domain.model.AuthUser
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val auth: FirebaseAuth,
) {

    suspend fun signInWithGoogle(idToken: String): Result<AuthUser> {
        return try {
            val firebaseCredential = GoogleAuthProvider.getCredential(
                idToken,
                null
            )

            Log.d("Mkdev","idToken : $idToken")

            val authResult = auth
                .signInWithCredential(firebaseCredential)
                .await()

            val user = authResult.user

            if (user != null ) {
                Result.success(AuthUser(user.uid , user.email ,user.displayName))
            }else{
                Result.failure(Exception("User is null"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}