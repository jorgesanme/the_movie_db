package com.jorgesm.themoviedb.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.jorgesm.themoviedb.R
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
import okhttp3.OkHttpClient
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
        "android.permission.ACCESS_COARSE_LOCATION"
    )
    
    @get:Rule(order = 3)
    val activityRule = ActivityScenarioRule(NavHostActivity::class.java)
    
    @Inject
    lateinit var movieDao: MovieDao
    
    @Inject
    lateinit var remoteDataSource: MovieRemoteDataSource
    @Inject
    lateinit var okHttpClient: OkHttpClient
    
    @Before
    fun setUp() {
        mockServer.server.enqueue(
            MockResponse().fromJson("popular_movies.json")
        )
        hiltRule.inject()
        val resource = OkHttp3IdlingResource.create("OkHttp", okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }
    
    /*
    Debido a gladle 8.2 el Idling que se usa no funciona correctamente.
    Por lo que se usa un sleep(), hasta que se encuentre solución
     */
    @Test
    fun click_on_a_movie_navigate_to_detail() {
        Thread.sleep(100)
        onView(withId(R.id.rv_covers))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,ViewActions.click()
                )
            )
        Thread.sleep(4_000)
        onView(withId(R.id.movie_detail_toolbar))
            .check(matches(ViewMatchers.hasDescendant(withText("Uncharted"))))
    }
}