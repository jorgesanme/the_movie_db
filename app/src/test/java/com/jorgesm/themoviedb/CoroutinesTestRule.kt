package com.jorgesm.themoviedb

import com.jorgesm.themoviedb.ui.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.kotlin.whenever

class CoroutinesTestRule: TestWatcher() {
    
    private val testDispatcher = StandardTestDispatcher()
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }
    
    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}