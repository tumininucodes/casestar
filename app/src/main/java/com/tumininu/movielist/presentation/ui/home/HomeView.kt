package com.tumininu.movielist.presentation.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
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

        val data = viewModel.movies.collectAsLazyPagingItems()

        Column {
            Column(
                modifier = modifier
//                .fillMaxSize()
                    .padding(padding),
//                verticalArrangement = Arrangement.Top,
            ) {
                LazyColumn {
                    items(data) {

                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(8.dp),
                    modifier = modifier.fillMaxHeight()
                ) {
//                    items(data.itemCount) { movie ->
//                        MovieView(movie = movie)
//                    }
                    items(data.itemCount, key = { it }) {
                        MovieView(movie = data[it]!!)
                    }
                }


//                when (data) {
//                    is NetworkResult.Loading -> {
//                        CircularProgressIndicator(
//                            modifier = modifier.align(Alignment.CenterHorizontally)
//                        )
//                    }
//                    is NetworkResult.Success -> {
//                        Column {
//                            LazyVerticalGrid(
//                                columns = GridCells.Fixed(3),
//                                horizontalArrangement = Arrangement.spacedBy(8.dp),
//                                contentPadding = PaddingValues(8.dp),
//                                modifier = modifier.fillMaxHeight()
//                            ) {
//                                items(data.data.results.size, key = { it }, span = {
//                                    GridItemSpan(maxLineSpan)
//                                }) {
//                                    MovieView(movie = data.data.results[it])
//                                }
//                                item {
//                                    pageCounter++
//                                    if (pageCounter > 1) {
//
//                                        showFetchMoreProgress.value = true
//                                        Toast.makeText(
//                                            LocalContext.current,
//                                            "scrolled to end, $pageCounter",
//                                            Toast.LENGTH_SHORT
//                                        ).show()
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    is NetworkResult.Error -> {
//                        Text(
//                            "Error fetching movies", modifier = modifier
//                                .align(Alignment.CenterHorizontally),
//                            textAlign = TextAlign.Center
//                        )
//                        Button(
//                            onClick = { viewModel.fetchMovies() },
//                            modifier = modifier.align(Alignment.CenterHorizontally)
//                        ) {
//                            Text(text = "Retry")
//                        }
//                    }
//                }
//
//
////            if (showFetchMoreProgress.value) {
////                CircularProgressIndicator(
////                    modifier = modifier
////                        .height(100.dp)
////                        .align(Alignment.CenterHorizontally)
////                )
////
////                Toast.makeText(
////                    LocalContext.current,
////                    "Progress, $pageCounter",
////                    Toast.LENGTH_SHORT
////                ).show()
////            }

            }

            CircularProgressIndicator(
                modifier = modifier
                    .height(100.dp)
                    .align(Alignment.CenterHorizontally)
            )

        }
    }
}