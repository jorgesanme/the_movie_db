package com.jorgesm.themoviedb.composeUI.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.utils.Constants


@Composable
fun MoviesGrid(movies: List<DomainMovie>, onMovieClick: (DomainMovie) -> Unit, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(110.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(movies) { movie ->
            MovieItem(
                movie = movie,
                onMovieClick = { onMovieClick(movie) }
            )
        }
    }
}

@Composable
fun MovieItem(movie: DomainMovie, onMovieClick: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxHeight()
        .clickable { onMovieClick() }) {

        Box {
            AsyncImage(
                model = Constants.IMG_BASE_URL + Constants.IMG_185 + movie.posterPath,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2 / 3f)
            )
            if (movie.isFavorite){
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(id = R.string.talk_back_favorite_icon),
                    tint = Color.White,
                    modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)
                )
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(8.dp)
            .heightIn(48.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = movie.title,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }

    }
}