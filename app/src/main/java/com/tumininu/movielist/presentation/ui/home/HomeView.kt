package com.tumininu.movielist.presentation.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
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

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
        ) {
            LazyColumn {
                items(data) {

                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp),
                modifier = modifier.fillMaxHeight()
            ) {
                items(data.itemCount, key = { it }) {
                    MovieView(movie = data[it]!!)
                }
            }

        }

    }
}