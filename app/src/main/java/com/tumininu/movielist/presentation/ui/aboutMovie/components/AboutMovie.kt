package com.tumininu.movielist.presentation.ui.aboutMovie.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tumininu.movielist.domain.model.Movie
import com.tumininu.movielist.presentation.ui.theme.Black
import com.tumininu.movielist.utils.NetworkImage

@Composable
fun AboutMovie(
    movie: Movie,
    modifier: Modifier = Modifier,
) {
    Box {
        Column(modifier = modifier
            .background(Black)
            .padding(horizontal = 16.dp)) {
            Text(text = movie.title, fontWeight = FontWeight.Bold, fontSize = 21.sp)
            Spacer(modifier.height(14.dp))
            Text(text = movie.overview.toString())
            Spacer(modifier.height(20.dp))

            Row {
                NetworkImage(
                    url = "https://image.tmdb.org/t/p/original" + movie.poster_path,
                    modifier = modifier
                        .width(150.dp)
                        .height(240.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(10.dp)))
                )

                Spacer(modifier = modifier.width(20.dp))

                Column {
                    Text(text = "Release date", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Spacer(modifier.height(2.dp))
                    Text(text = movie.release_date.toString(), fontSize = 15.sp)
                    Spacer(modifier.height(8.dp))
                    Text(text = "Vote count", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Spacer(modifier.height(2.dp))
                    Text(text = movie.vote_count.toString(), fontSize = 15.sp)
                    Spacer(modifier.height(8.dp))
                    Text(text = "Average rating", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Spacer(modifier.height(2.dp))
                    Text(text = movie.vote_average.toString(), fontSize = 15.sp)
                    Spacer(modifier.height(8.dp))
                    Text(text = "Popularity score", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Spacer(modifier.height(2.dp))
                    Text(text = movie.popularity.toString(), fontSize = 15.sp)
                    Spacer(modifier.height(8.dp))
                    Text(text = "Language", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Spacer(modifier.height(2.dp))
                    Text(text = movie.original_language.toString(), fontSize = 15.sp)
                }
            }

        }
    }
}