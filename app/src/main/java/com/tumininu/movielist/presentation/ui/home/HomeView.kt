package com.tumininu.movielist.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
@androidx.compose.Composable
fun HomeView(modifier: Modifier = Modifier) {

    val viewModel = remember { MainViewModel() }

    Scaffold(topBar = {
        TopAppBar(backgroundColor = Black) {
            Spacer(modifier = modifier.width(8.dp))
            if (viewModel.navigateToAboutMovie.value) {
                Image(painter = painterResource(id = R.drawable.ic_round_arrow_back_24),
                    contentDescription = "Back arrow", modifier = modifier.clickable {
                        viewModel.navigateToAboutMovie.value = false
                    })
            }
            Text(text = viewModel.appBarTitle.value, fontSize = 22.sp, color = White)
        }
    }) { padding ->

        val data = viewModel.movies.collectAsLazyPagingItems()

        if (viewModel.navigateToAboutMovie.value) {
            viewModel.currentMovie?.let { AboutMovie(movie = it) }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = modifier
                    .fillMaxSize()
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