package com.tumininu.movielist.presentation.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tumininu.movielist.model.NetworkResult
import com.tumininu.movielist.presentation.HomeViewModel
import com.tumininu.movielist.presentation.ui.home.components.MovieView

@Composable
fun HomeView(modifier: Modifier = Modifier) {

    val viewModel = remember { HomeViewModel() }

    Scaffold(topBar = {
        TopAppBar {
            Spacer(modifier = modifier.width(8.dp))
            Text(text = "Casestar", fontSize = 22.sp)
        }
    }) { padding ->
        val data = viewModel.getMovies().collectAsState().value
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
        ) {
            when (data) {
                is NetworkResult.Loading -> {
                    CircularProgressIndicator(
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    )
                }
                is NetworkResult.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(data.data.results.size, key = { it }) {
                            MovieView(movie = data.data.results[it])
                        }
                    }
                }
                is NetworkResult.Error -> {
                    Text(
                        "Error fetching movies", modifier = modifier
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = { viewModel.fetchMovies() },
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Retry")
                    }
                }
            }
        }
    }
}