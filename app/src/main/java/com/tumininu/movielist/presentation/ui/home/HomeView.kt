package com.tumininu.movielist.presentation.ui.home

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import com.tumininu.movielist.R
import com.tumininu.movielist.presentation.ui.MainViewModel
import com.tumininu.movielist.presentation.ui.aboutMovie.AboutMovie
import com.tumininu.movielist.presentation.ui.home.components.MovieView
import com.tumininu.movielist.presentation.ui.theme.Black
import com.tumininu.movielist.presentation.ui.theme.White

@Composable
fun HomeView(modifier: Modifier = Modifier, viewModel: MainViewModel) {

    Scaffold(topBar = {
        TopAppBar(backgroundColor = Black) {
            if (viewModel.navigateToAboutMovie.value) {
                Row {
                    Spacer(modifier = modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_round_arrow_back_24),
                        contentDescription = "Back arrow", modifier = modifier.clickable {
                            viewModel.navigateToAboutMovie.value = false
                        }
                    )
                }
            }
            Spacer(modifier = modifier.width(8.dp))
            Text(text = viewModel.appBarTitle.value,
                fontSize = 22.sp,
                color = White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
        }
    }) { padding ->

        val data = viewModel.movies.collectAsLazyPagingItems()
        val density = LocalDensity.current
        val listState = rememberLazyGridState()

        AnimatedVisibility(
            visible = viewModel.navigateToAboutMovie.value,
            enter = slideInHorizontally {
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(
                initialAlpha = 0.3f
            ),
            exit = slideOutHorizontally() + shrinkVertically() + fadeOut()
        ) {
            viewModel.currentMovie?.let {
                AboutMovie(movie = it)
            }
        }

        if (viewModel.navigateToAboutMovie.value.not()) {
            LazyVerticalGrid(
                state = listState,
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = modifier
                    .fillMaxSize()
                    .background(Black)
                    .padding(padding),
            ) {
                items(data.itemCount, key = { it }) {
                    MovieView(movie = data[it]!!, onClick = {
                        viewModel.navigateToAboutMovie.value = true
                        viewModel.currentMovie = data[it]
                    })
                }
            }
        }
    }
}