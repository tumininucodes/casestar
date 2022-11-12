package com.tumininu.movielist.presentation.ui.home

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import com.tumininu.movielist.R
import com.tumininu.movielist.presentation.MainViewModel
import com.tumininu.movielist.presentation.ui.aboutMovie.AboutMovieActivity
import com.tumininu.movielist.presentation.ui.home.components.MovieView
import com.tumininu.movielist.presentation.ui.search.SearchActivity
import com.tumininu.movielist.presentation.ui.theme.Black
import com.tumininu.movielist.presentation.ui.theme.White

@Composable
fun HomeView(modifier: Modifier = Modifier, viewModel: MainViewModel, activity: Activity) {

    Scaffold(topBar = {
        TopAppBar(backgroundColor = Black) {
            Spacer(modifier = modifier.width(8.dp))
            Text(text = "Casestar",
                fontSize = 22.sp,
                color = White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis)
            Spacer(modifier = modifier.fillMaxWidth(0.88f))
            Column {
                Spacer(modifier = modifier.height(8.dp))
                Image(painter = painterResource(id = R.drawable.ic_round_search_24),
                    contentDescription = "Search",
                    modifier = modifier
                        .size(40.dp)
                        .background(color = Black)
                        .clip(RoundedCornerShape(size = 4.dp))
                        .clickable {
                            activity.startActivity(Intent(activity, SearchActivity::class.java))
                        }
                        .padding(5.dp)
                )
            }
        }
    }) { padding ->

        val data = viewModel.movies.collectAsLazyPagingItems()
        val listState = rememberLazyGridState()

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
                    viewModel.currentMovie = data[it]
                    val intent = Intent(activity, AboutMovieActivity::class.java)
                    intent.putExtra("movie", viewModel.currentMovie)
                    activity.startActivity(intent)
                })
            }
        }
    }
}