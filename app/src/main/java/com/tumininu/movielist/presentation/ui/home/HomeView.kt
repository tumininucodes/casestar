package com.tumininu.movielist.presentation.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tumininu.movielist.model.NetworkResult
import com.tumininu.movielist.presentation.HomeViewModel
import com.tumininu.movielist.presentation.ui.home.components.MovieView

@Composable
fun HomeView(modifier: Modifier = Modifier) {

    val viewModel = remember { HomeViewModel() }

    Scaffold(topBar = {
        TopAppBar {
            Spacer(modifier = modifier.width(8.dp))
            Text(text = "Casestar")
        }
    }) { padding ->
        val data = viewModel.getMovies().collectAsState()
        Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            when (data.value) {
                is NetworkResult.Loading -> {
                    CircularProgressIndicator(
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    )
                }
                is NetworkResult.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = modifier.padding(start = 16.dp, end = 16.dp)
                    ) {
                        items(30) {
                            MovieView()
                        }
                    }
                }
                is NetworkResult.Error -> {
                    Text(
                        "Error fetching movies", modifier = modifier
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}