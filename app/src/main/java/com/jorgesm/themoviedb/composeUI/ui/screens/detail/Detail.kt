package com.jorgesm.themoviedb.composeUI.ui.screens.detail


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.jorgesm.themoviedb.R
import com.jorgesm.themoviedb.domain.DomainMovie
import com.jorgesm.themoviedb.ui.detail.DetailViewModel
import com.jorgesm.themoviedb.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Details(vm: DetailViewModel = hiltViewModel(), onBackClick: () -> Unit) {
    val state by vm.state.collectAsState()
    val topAppBarTitle = state.movie?.title ?: ""
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = {
            DetailTopAppBar(
                title = topAppBarTitle,
                onBackClick = onBackClick,
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            FloatingActionButton(onClick = vm::onFavoriteClicked) {
                Icon(
                    imageVector = if (state.movie?.isFavorite == true)
                        Icons.Default.Favorite
                    else
                        Icons.Default.FavoriteBorder,

                    contentDescription = stringResource(id = R.string.talk_back_favorite_icon)
                )
            }
        }
    ) { padding ->
        state.movie?.let {
            MovieContent(
                it,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
fun MovieContent(movie: DomainMovie, modifier: Modifier) {
    Column(modifier = modifier
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
        AsyncImage(
            model = Constants.IMG_BASE_URL + Constants.IMG_185 + movie.backdropPath,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            text = movie.overview,
            Modifier.padding(horizontal = 8.dp, vertical = 4.dp ),
            fontSize = 16.sp,
            textAlign = TextAlign.Justify
        )
        Text(
            text = buildAnnotatedString { ->
                Property(name = "Original language", value = movie.originalLanguage)
                Property(name = "Original Title", value = movie.originalTitle)
                Property(name = "Original Title", value = movie.originalTitle)
                Property(name = "Release Day", value = movie.releaseDate)
                Property(name = "Release Day", value = movie.releaseDate)
                Property(name = "Popularity", value = movie.popularity.toString())
                Property(
                    name = "Vote Average",
                    value = movie.voteAverage.toString(),
                    isLastItem = true
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.padding(vertical = 16.dp))

    }
}

@Composable
fun AnnotatedString.Builder.Property(name: String, value: String, isLastItem: Boolean = false) {
    withStyle(ParagraphStyle(lineHeight = 18.sp)) {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("$name :") }
        append(value)
        if (!isLastItem) {
            append("\n")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Justify,

                )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = stringResource(id = R.string.talk_back_previous_screen)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}