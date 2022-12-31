package com.tumininu.movielist.presentation.ui.home

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import com.tumininu.movielist.presentation.MainViewModel
import com.tumininu.movielist.presentation.ui.aboutApp.AboutAppActivity
import com.tumininu.movielist.presentation.ui.aboutMovie.AboutMovieActivity
import com.tumininu.movielist.presentation.ui.home.components.MovieView
import com.tumininu.movielist.presentation.ui.search.SearchActivity
import com.tumininu.movielist.presentation.ui.theme.Black
import com.tumininu.movielist.presentation.ui.theme.White
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel(),
    activity: Activity,
) {

    val showOptionsMenu = remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "Casestar",
                    fontSize = 22.sp,
                    color = White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
            },
            backgroundColor = Black,
            actions = {
                Column {
                    Spacer(modifier = modifier.height(2.dp))
                    IconButton(
                        onClick = {
                            activity.startActivity(Intent(activity, SearchActivity::class.java))
                        },
                        modifier = modifier.size(40.dp)
                    ) {
                        Icon(Icons.Default.Search, "Search", tint = Color.White)
                    }
                }
                IconButton(
                    onClick = { showOptionsMenu.value = !showOptionsMenu.value },
                    modifier = modifier.size(40.dp)
                ) {
                    Icon(Icons.Default.MoreVert, "More", tint = Color.White)
                }
                DropdownMenu(
                    expanded = showOptionsMenu.value,
                    onDismissRequest = { showOptionsMenu.value = false },
                ) {
                    DropdownMenuItem(onClick = {
                        activity.startActivity(Intent(activity, AboutAppActivity::class.java))
                        showOptionsMenu.value = false
                    }) {
                        Text(text = "About")
                    }
                }
            }
        )
    }) { padding ->

        val data = viewModel.movies.collectAsLazyPagingItems()
        val listState = rememberLazyGridState()

        LazyVerticalGrid(
            state = listState,
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = modifier
                .fillMaxSize()
                .background(Black)
                .padding(padding),
        ) {
            items(data.itemCount, key = { it }) {
                MovieView(movie = data[it]!!, onClick = {
                    viewModel.currentMovie = data[it]
                    val intent = Intent(activity, AboutMovieActivity::class.java)
                    intent.putExtra("movie", viewModel.currentMovie)
                    activity.startActivity(intent)
                })
            }
        }
    }
}