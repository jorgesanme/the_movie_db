package com.jorgesm.themoviedb.ui.main

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.jorgesm.themoviedb.appTestShared.buildDatabaseMovies
import com.jorgesm.themoviedb.data.database.MovieDao
import com.jorgesm.themoviedb.di.AppModule
import com.jorgesm.themoviedb.ui.NavHostActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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
    val locationPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        "android.permission.ACCESS_COARSE_LOCATION"
    )
    
    @get:Rule(order = 2)
    val activityRule = ActivityScenarioRule(NavHostActivity::class.java)
    
    @Inject
    lateinit var movieDao:MovieDao
    @Before
    fun setUp(){
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
}