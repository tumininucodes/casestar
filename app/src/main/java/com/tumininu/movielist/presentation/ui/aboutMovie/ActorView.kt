package com.tumininu.movielist.presentation.ui.aboutMovie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.tumininu.movielist.utils.NetworkImage

@Composable
fun ActorView(name: String, image: String, character: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
//            .height(200.dp)
            .width(100.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Column {
            NetworkImage(
                url = "https://image.tmdb.org/t/p/original$image",
                modifier = modifier
                    .height(150.dp)
                    .width(100.dp)
            )
            Text(text = name)
            Text(text = character)
        }
    }


}