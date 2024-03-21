
import com.jorgesm.themoviedb.data.PermissionChecker
import com.jorgesm.themoviedb.data.PermissionChecker.Permission.COARSE_LOCATION
import com.jorgesm.themoviedb.data.RegionRepository
import com.jorgesm.themoviedb.data.RegionRepository.Companion.DEFAULT_REGION
import com.jorgesm.themoviedb.data.datasource.LocationDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


class RegionRepositoryTest {
    
    @Test
    fun `Return default region when coarse permission not granted`(): Unit =
        runBlocking {
            val regionRepository = buildRegionRepository(
                permissionChecker = mock{on{check(COARSE_LOCATION)} doReturn false}
            )
            val defaultRegion = DEFAULT_REGION
            val region = regionRepository.findLasRegion()
            
            assertEquals(defaultRegion, region)
        }
    
    @Test
    fun `Return region from location data when permission granted`(): Unit =
        runBlocking {
            val regionRepository = buildRegionRepository(
                locationDataSource = mock {
                    onBlocking { findLastLocation()} doReturn "ES"},
                permissionChecker = mock {on { check(COARSE_LOCATION) } doReturn false}
            )
            val defaultRegion = "ES"
            val region = regionRepository.findLasRegion()
            
            assertEquals(defaultRegion, region)
        }
        
    
}

private fun buildRegionRepository(
    locationDataSource: LocationDataSource = mock(),
    permissionChecker: PermissionChecker = mock()
)= RegionRepository(locationDataSource, permissionChecker)