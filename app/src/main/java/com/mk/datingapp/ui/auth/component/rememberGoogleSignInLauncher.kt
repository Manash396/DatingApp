package com.mk.datingapp.ui.auth.component

import android.app.Instrumentation
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Composable
fun rememberGoogleSignInLauncher(
    onSuccess: (String?, String?) -> Unit
): Pair<GoogleSignInClient, ManagedActivityResultLauncher<Intent, ActivityResult>> {

    val context = LocalContext.current

    val client = GoogleSignIn.getClient(
        context,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    )

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

        try {
            val account = task.result
            onSuccess(account.email, account.displayName)
        } catch (e: Exception) {
            Log.e("GOOGLE", e.message, e)
        }
    }

    return client to launcher
}