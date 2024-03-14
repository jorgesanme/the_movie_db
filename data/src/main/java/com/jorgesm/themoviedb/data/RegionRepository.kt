package com.jorgesm.themoviedb.data


import com.jorgesm.themoviedb.data.PermissionChecker.Permission.COARSE_LOCATION
import com.jorgesm.themoviedb.data.datasource.LocationDataSource


class RegionRepository(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionChecker
) {
    companion object{
        private const val DEFAULT_REGION = "US"
    }
    
    suspend fun findLasRegion(): String {
        return if(permissionChecker.check(COARSE_LOCATION))
            locationDataSource.findLastLocation() ?: DEFAULT_REGION
        else
            DEFAULT_REGION
    }
}