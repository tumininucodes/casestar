package com.tumininu.movielist.presentation.ui.aboutMovie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tumininu.movielist.domain.model.Movie
import com.tumininu.movielist.utils.NetworkImage

@Composable
fun AboutMovie(movie: Movie, modifier: Modifier = Modifier) {

    Column {
        NetworkImage(
            url = "https://image.tmdb.org/t/p/original" + movie.poster_path,
            modifier = modifier
                .height(400.dp)
                .width(400.dp)
        )
        Text(text = movie.title)
        Text(text = movie.overview.toString())
        Text(text = movie.release_date.toString())

    }

}