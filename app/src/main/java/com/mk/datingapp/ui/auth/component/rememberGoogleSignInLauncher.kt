package com.mk.datingapp.ui.auth.component

import android.app.Instrumentation
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.launch

@Composable
fun rememberGoogleSignInLauncher(
    onResult: (String?) -> Unit
): () -> Unit {

    val context  =  LocalContext.current
    val coroutineScope  = rememberCoroutineScope()

    val googleIdOption = GetGoogleIdOption.Builder()
        .setServerClientId("650743784297-hlkt28apl66nt6ps31arki84anftgf8h.apps.googleusercontent.com")
        .setFilterByAuthorizedAccounts(false)
        .build()

    val request  = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    val credentialManager = CredentialManager.create(context)

    return  {
        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    context , request
                )

                val credential = result.credential

                if (
                    credential is CustomCredential &&
                    credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                ) {
                    val googleCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)

                    val idToken = googleCredential.idToken

                    Log.d("Mkdev",idToken)

                    onResult(idToken) // SUCCESS

                } else {
                    onResult(null) //  invalid
                }

            }catch (e: Exception){
                Log.d("Mkdev",e.message.toString())
                onResult(null)
            }
        }
    }
}