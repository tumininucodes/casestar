package com.tumininu.movielist.presentation.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.tumininu.movielist.R

@Composable
fun MovieView(modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(top = 8.dp, bottom = 8.dp)) {
        Column {
            GlideImage(
                imageModel = "https://images.news18.com/ibnlive/uploads/2022/07/thor-love-and-thunder.jpg",
                imageOptions = ImageOptions(contentScale = ContentScale.Crop),
                modifier = modifier
                    .width(120.dp)
                    .height(200.dp)
                    .align(Alignment.CenterHorizontally),
                previewPlaceholder = R.drawable.image,
            )
            Text(
                text = "Movie title",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle2,
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(8.dp)
            )
            Text(
                text = "Description of the movie",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle2,
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
fun ComposablePreview() {
    MovieView()
}