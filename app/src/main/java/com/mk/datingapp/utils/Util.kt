package com.mk.datingapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import java.util.Locale


object Util {
    @SuppressLint("MissingPermission")
    fun getUserLocation(
        context: Context,
        onLocationResult: (String) -> Unit
    ) {
        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).addOnSuccessListener { location ->

//            Log.d("KRISHNA", location.toString())

            if (location != null) {

                val lat = location.latitude
                val lon = location.longitude

                getAddressFromLatLng(context, lat, lon, onLocationResult)

            } else {
                onLocationResult("Unable to fetch location")
            }
        }
    }

    fun getAddressFromLatLng(
    context: Context,
    lat: Double,
    lon: Double,
    onLocationResult: (String) -> Unit
    ) {

        val geoCoder  = Geocoder(context, Locale.getDefault())


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
//            Log.d("KRISHNA", "I entered tiramisu ")

            geoCoder.getFromLocation(
                lat , lon, 1
            ){ addresses ->
                Log.d("KRISHNA", "address $addresses")

                if (addresses.isNotEmpty()){
                    val address = addresses[0]
                    Log.d("KRISHNA", "address $address")


                    val city  =  address.locality ?: ""
                    val state  =  address.adminArea?: ""

                    val locationText  = listOf(
                        city , state
                    ).filter { !it.isEmpty() }
                        .joinToString(", ")

                    Log.d("KRISHNA", "location Address :$locationText")


                    onLocationResult(locationText.ifEmpty { "Unknown Location" })
                }else{
                    onLocationResult("Unknown Location")
                }
            }
        }else{
            try {
                val addresses = geoCoder.getFromLocation(lat, lon, 1)

                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]

                    val city = address.locality ?: ""
                    val state = address.adminArea ?: ""

                    val locationText = listOf(city, state)
                        .filter { it.isNotEmpty() }
                        .joinToString(", ")

                    onLocationResult(locationText.ifEmpty { "Unknown location" })
                } else {
                    onLocationResult("Unknown location")
                }

            } catch (e: Exception) {
                onLocationResult("Location error")
            }
        }

    }


    fun getAlpha(scale: Float) = (1f - scale).coerceIn(0f, 1f)

}