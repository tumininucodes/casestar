package com.tumininu.movielist.presentation.ui.aboutMovie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tumininu.movielist.utils.NetworkImage

@Composable
fun ActorView(name: String, image: String, character: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .height(220.dp)
            .width(110.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Column {
            NetworkImage(
                url = "https://image.tmdb.org/t/p/original$image",
                modifier = modifier
                    .height(150.dp)
                    .width(110.dp)
            )
            Text(text = name,
                fontSize = 12.sp,
                modifier = modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
                maxLines = 2)
            Text(text = character,
                fontSize = 12.sp,
                modifier = modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 8.dp),
                maxLines = 2)
        }
    }


}