package com.jorgesm.themoviedb.ui.main

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.jorgesm.themoviedb.appTestShared.buildDatabaseMovies
import com.jorgesm.themoviedb.data.database.MovieDao
import com.jorgesm.themoviedb.data.datasource.MovieRemoteDataSource
import com.jorgesm.themoviedb.data.server.MockWebServerRule
import com.jorgesm.themoviedb.data.server.fromJson
import com.jorgesm.themoviedb.di.AppModule
import com.jorgesm.themoviedb.ui.NavHostActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class MainInstrumentationTest {
    
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)
    
    @get:Rule(order = 1)
    val mockServer = MockWebServerRule()
    
    @get:Rule(order = 2)
    val locationPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        "android.permission.ACCESS_COARSE_LOCATION")
    
    @get:Rule(order = 3)
    val activityRule = ActivityScenarioRule(NavHostActivity::class.java)
    
    @Inject
    lateinit var movieDao:MovieDao
    
    @Inject
    lateinit var remoteDataSource: MovieRemoteDataSource
    
    @Before
    fun setUp(){
        mockServer.server.enqueue(
            MockResponse().fromJson("popular_movies.json")
        )
        hiltRule.inject()
    }
    
    @Test
    fun test_it_works() {
    Thread.sleep(2_000)
    }
    
    @Test
    fun test_4_items_count_db() = runTest{
        movieDao.insertMovie(buildDatabaseMovies(1,2,3,4))
        assertEquals(4, movieDao.countMovies())
    }
    
    @Test
    fun test_6_items_count_db() = runTest{
        movieDao.insertMovie(buildDatabaseMovies(1,2,3,4,5,6))
        assertEquals(6, movieDao.countMovies())
    }
    
    @Test
    fun check_mock_server_in_working() = runTest {
        val movie = remoteDataSource.findPopularMovies("US")
        movie.fold({ throw Exception(it.toString()) }){
            assertEquals("The Batman", it[0].title)
        }
    
    }
}