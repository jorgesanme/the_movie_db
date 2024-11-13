package com.jorgesm.themoviedb.composeUI.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopAppBar(title: String, scrollBehavior: TopAppBarScrollBehavior) {
    TopAppBar(title = { Text(text = title) }, scrollBehavior = scrollBehavior)
}