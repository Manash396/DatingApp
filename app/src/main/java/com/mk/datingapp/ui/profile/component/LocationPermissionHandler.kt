package com.mk.datingapp.ui.profile.component

import android.app.Activity
import android.content.Context
import android.location.LocationRequest
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionHandler(
    context: Context,
    onGranted: () -> Unit,
    onNotGranted:() ->  Unit
) {

    val permissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) { isGranted ->
        if (isGranted) {
            onGranted()
        }
    }

//    location setting launcher

    val settingLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            onGranted()
        }else{
            onNotGranted()
        }
    }



    LaunchedEffect(permissionState.status) {
        when (permissionState.status) {

            is PermissionStatus.Granted -> {
                checkLocationSetting(context , onGranted , settingLauncher)
            }

            is PermissionStatus.Denied -> {
                val denied = permissionState.status as PermissionStatus.Denied

                if (denied.shouldShowRationale) {

                } else {
                    //  Permanently denied
                }
            }
        }
    }


    LaunchedEffect(Unit) {
        if (permissionState.status is PermissionStatus.Granted) {
            checkLocationSetting(context , onGranted , settingLauncher)
        } else {
            permissionState.launchPermissionRequest()
        }
    }
}

fun checkLocationSetting(
    context: Context,
    onGranted: () -> Unit,
    settingLauncher: ActivityResultLauncher<IntentSenderRequest>
) {

    val locationRequest = com.google.android.gms.location.LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY, 1000
    ).build()

    val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)

    val client = LocationServices.getSettingsClient(context)

    val task = client.checkLocationSettings(builder.build())

    task.addOnSuccessListener {
        onGranted()
    }

    task.addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
            try {
                val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution).build()
                settingLauncher.launch(intentSenderRequest)
            } catch (e: Exception) {
                Log.e("MKDEV", "Failed to launch location settings dialog", e)
            }
        }
    }
}