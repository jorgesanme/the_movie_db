package com.jorgesm.themoviedb.composeUI.ui.screens.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.domain.Error


@Composable
fun ErrorText(error: Error, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.ErrorOutline,
                contentDescription = "Error happen",
                modifier = Modifier.padding(24.dp)
            )
            Text(text = error.toUiString(), textAlign = TextAlign.Center)
        }
    }
}
@Composable
private fun Error.toUiString() = when(this){
    Error.Connectivity -> stringResource(id = R.string.connectivity_error)
    is Error.Server -> stringResource(id = R.string.server_error) + code
    is Error.Unknown -> stringResource(id = R.string.unknown_error) + message
}