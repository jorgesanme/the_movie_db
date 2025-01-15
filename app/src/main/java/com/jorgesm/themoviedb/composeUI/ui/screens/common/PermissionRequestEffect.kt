package com.jorgesm.themoviedb.composeUI.ui.screens.common

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun PermissionRequestEffect(permission: String, onResult:(Boolean)->Unit) {
    val permissionsLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission() ) {
        onResult.invoke(it)
    }
    LaunchedEffect(key1 = Unit) {
        permissionsLauncher.launch(permission)
    }
}