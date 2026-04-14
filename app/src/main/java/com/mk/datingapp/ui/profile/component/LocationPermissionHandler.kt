package com.mk.datingapp.ui.profile.component

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn( ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionHandler(
    context: Context,
    onGranted: () -> Unit
) {
//    val context = LocalContext.current

    val permissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ){isGranted ->
        if (isGranted){
            onGranted()
//            Log.d("KRISHNA", "Granted is called ")
        }
    }


    LaunchedEffect(permissionState.status) {
//        Log.d("KRISHNA", permissionState.status.toString())
        when (permissionState.status) {

            is PermissionStatus.Granted -> {
                onGranted()
            }

            is PermissionStatus.Denied -> {
                val denied = permissionState.status as PermissionStatus.Denied

                if (denied.shouldShowRationale) {
                    // Ask again
//                    permissionState.launchPermissionRequest()

                } else {
                    //  Permanently denied
//                    Toast.makeText(
//                        context,
//                        "Enable location permission from settings",
//                        Toast.LENGTH_LONG
//                    ).show()

//                    openAppSettings(context)
                }
            }
        }
    }

    // Trigger request when this composable is called
    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
//        Log.d("KRISHNA", "permission is launched ")
    }

    LaunchedEffect(Unit) {

        if (permissionState.status is PermissionStatus.Granted) {
            onGranted()
//            Log.d("KRISHNA", "Granted is called ")
        } else {
            permissionState.launchPermissionRequest()
        }
    }
}