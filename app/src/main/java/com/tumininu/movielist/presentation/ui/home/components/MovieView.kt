package com.tumininu.movielist.presentation.ui.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tumininu.movielist.domain.model.Movie
import com.tumininu.movielist.utils.NetworkImage

@Composable
fun MovieView(movie: Movie, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(top = 8.dp, bottom = 8.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        NetworkImage(
            url = "https://image.tmdb.org/t/p/original" + movie.poster_path,
            modifier = modifier
        )
    }
}