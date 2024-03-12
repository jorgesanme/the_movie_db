package com.jorgesm.themoviedb.framework.datasource

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.jorgesm.themoviedb.data.datasource.LocationDataSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PlayServicesLocationDataSource(application: Application): LocationDataSource {
    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)
    
    @SuppressLint("MissingPermission")
    override suspend fun findLastLocation(): Location? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationProviderClient.lastLocation
                .addOnCompleteListener{
                    continuation.resume(it.result)
                }
        }
}