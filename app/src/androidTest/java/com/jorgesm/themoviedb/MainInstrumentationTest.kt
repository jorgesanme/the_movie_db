package com.jorgesm.themoviedb

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.jorgesm.themoviedb.ui.NavHostActivity
import org.junit.Rule
import org.junit.Test

class MainInstrumentationTest {
    @get:Rule
    val locationPermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        "android.permission.ACCESS_COARSE_LOCATION"
    )
    
    @get:Rule
    val activityRule = ActivityScenarioRule(NavHostActivity::class.java)
    
    @Test
    
    fun test_it_works(){
    
    }
}