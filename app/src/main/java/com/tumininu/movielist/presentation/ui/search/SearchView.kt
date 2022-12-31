package com.tumininu.movielist.presentation.ui.search

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.tumininu.movielist.domain.model.MovieSearchResponse
import com.tumininu.movielist.domain.model.NetworkResult
import com.tumininu.movielist.presentation.SearchViewModel
import com.tumininu.movielist.presentation.ui.aboutMovie.AboutMovieActivity
import com.tumininu.movielist.presentation.ui.home.components.MovieView
import com.tumininu.movielist.presentation.ui.search.components.Search
import com.tumininu.movielist.presentation.ui.theme.Black
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel(),
    activity: Activity,
) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val listState = rememberLazyGridState()
    val data: MutableState<NetworkResult<MovieSearchResponse>> =
        remember { mutableStateOf(NetworkResult.Loading) }

    Column(modifier.fillMaxSize()) {
        Search(activity, textState, viewModel, data)
        when (data.value) {
            is NetworkResult.Loading -> {
                if (textState.value.text.isNotEmpty()) {
                    Spacer(modifier = modifier.fillMaxHeight(0.45f))
                    CircularProgressIndicator(modifier
                        .align(Alignment.CenterHorizontally))
                    Spacer(modifier = modifier.fillMaxHeight(0.4f))
                }
            }
            is NetworkResult.Success -> {
                val response = data.value as NetworkResult.Success<MovieSearchResponse>
                LazyVerticalGrid(
                    state = listState,
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = modifier
                        .fillMaxSize()
                        .background(Black)
                ) {
                    items(response.data.results.size, key = { it }) {
                        MovieView(movie = response.data.results[it], onClick = {
                            viewModel.currentMovie = response.data.results[it]
                            val intent = Intent(activity, AboutMovieActivity::class.java)
                            intent.putExtra("movie", viewModel.currentMovie)
                            activity.startActivity(intent)
                        })
                    }
                }
            }
            is NetworkResult.Error -> {
                Spacer(modifier = modifier.fillMaxHeight(0.45f))
                Text(text = "Error fetching movies",
                    modifier = modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = modifier.fillMaxHeight(0.4f))

            }
        }
    }
}