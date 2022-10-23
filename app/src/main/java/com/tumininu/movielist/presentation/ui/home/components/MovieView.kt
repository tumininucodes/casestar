package com.tumininu.movielist.presentation.ui.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.tumininu.movielist.domain.model.Movie
import com.tumininu.movielist.utils.NetworkImage

@Composable
fun MovieView(movie: Movie, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .height(200.dp)
            .width(150.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        NetworkImage(
            url = "https://image.tmdb.org/t/p/original" + movie.poster_path,
            modifier = modifier
        )
    }
}