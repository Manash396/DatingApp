package com.mk.datingapp.data.remote.firebase.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.mk.datingapp.data.local.UserPreferences
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val store: FirebaseFirestore,
    private val prefs: UserPreferences,
) {

     suspend fun checkAndCreateUser(
        uid: String,
        email: String?,
        name: String?
    ): Boolean {

        val docRef = store.collection("users").document(uid)

        val snapshot = docRef.get().await()

         Log.d("Mkdev","snapShot: $snapshot")

        return if (!snapshot.exists()) {

            val userMap = mapOf(
                "uid" to uid,
                "email" to email,
                "name" to name,
                "createdAt" to System.currentTimeMillis()
            )

            docRef.set(userMap).await()

            prefs.saveUser(uid,email ?: "",name ?: "")

            true

        } else {
            false
        }
    }
}