package com.tumininu.movielist.presentation.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tumininu.movielist.model.NetworkResult
import com.tumininu.movielist.presentation.HomeViewModel
import com.tumininu.movielist.presentation.ui.home.components.MovieView
import com.tumininu.movielist.presentation.ui.theme.Black
import com.tumininu.movielist.presentation.ui.theme.White

@Composable
fun HomeView(modifier: Modifier = Modifier) {

    val viewModel = remember { HomeViewModel() }

    Scaffold(topBar = {
        TopAppBar(backgroundColor = Black) {
            Spacer(modifier = modifier.width(8.dp))
            Text(text = "Casestar", fontSize = 22.sp, color = White)
        }
    }) { padding ->

        val data = viewModel.getMovies().collectAsState().value
        var showFetchMoreProgress = remember { mutableStateOf(false) }
        var pageCounter = 0
        val scrollState = rememberLazyListState()
        // observer when reached end of list
//        val endOfListReached = remember {
//            derivedStateOf {
//                scrollState.isScrolledToEnd()
//            }
//        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Top,
        ) {
            when (data) {
                is NetworkResult.Loading -> {
                    CircularProgressIndicator(
                        modifier = modifier.align(Alignment.CenterHorizontally)
                    )
                }
                is NetworkResult.Success -> {
                    Column {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(4),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(8.dp),
                            modifier = modifier
                        ) {
                            items(data.data.results.size, key = { it }) {
                                MovieView(movie = data.data.results[it])
                            }
                            item {
                                pageCounter++
                                if (pageCounter > 1) {

                                    showFetchMoreProgress.value = true
                                    Toast.makeText(
                                        LocalContext.current,
                                        "scrolled to end, $pageCounter",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
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

            if (showFetchMoreProgress.value) {
                CircularProgressIndicator(
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                )

                Toast.makeText(
                    LocalContext.current,
                    "Progress, $pageCounter",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
}