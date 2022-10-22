package com.tumininu.movielist.presentation.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tumininu.movielist.domain.model.Movie
import com.tumininu.movielist.utils.NetworkImage

@Composable
fun MovieView(movie: Movie, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(top = 8.dp, bottom = 8.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = modifier
        ) {
            NetworkImage(
                url = "https://image.tmdb.org/t/p/original" + movie.poster_path,
                modifier = modifier
                    .width(90.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = modifier.height(8.dp))
            Text(
                text = movie.title,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 8.dp)
            )
            Spacer(modifier = modifier.height(8.dp))
        }
    }
}

//@Preview
//@Composable
//fun ComposablePreview() {
//    MovieView()
//}