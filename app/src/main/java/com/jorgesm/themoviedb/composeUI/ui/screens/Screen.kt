package com.jorgesm.themoviedb.composeUI.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jorgesm.themoviedb.composeUI.ui.theme.TheMovieDbTheme

@Composable
fun Screen(content: @Composable () -> Unit) {
    TheMovieDbTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            content = content
        )
    }
}