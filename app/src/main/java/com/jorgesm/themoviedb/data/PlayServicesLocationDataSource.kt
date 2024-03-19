package com.jorgesm.themoviedb.data

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.jorgesm.themoviedb.data.datasource.LocationDataSource
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class PlayServicesLocationDataSource @Inject constructor(application: Application): LocationDataSource {
    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)
    private val geocoder = Geocoder(application)
    
    @SuppressLint("MissingPermission")
    override suspend fun findLastLocation(): String? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationProviderClient.lastLocation
                .addOnCompleteListener{
                    continuation.resume(it.result.toRegion())
                }
        }
    
    private fun Location?.toRegion(): String? {
        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.countryCode
    }
}