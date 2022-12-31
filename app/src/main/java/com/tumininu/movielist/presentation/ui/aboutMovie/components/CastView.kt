package com.tumininu.movielist.presentation.ui.aboutMovie

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.dp
import com.tumininu.movielist.domain.model.CastResponse
import com.tumininu.movielist.domain.model.NetworkResult

@Composable
fun CastView(data: MutableState<NetworkResult<CastResponse>>) {
    when (data.value) {
        is NetworkResult.Loading -> {
            CircularProgressIndicator()
        }
        is NetworkResult.Success -> {
            LazyRow(contentPadding = PaddingValues(8.dp), content = {
                items(items = (data.value as NetworkResult.Success<CastResponse>).data.cast) {
                    ActorView(name = it.name.toString(),
                        image = it.profile_path.toString(),
                        character = it.character.toString())
                }
            })
        }
        is NetworkResult.Error -> {
            Text(text = ((data.value as NetworkResult.Error).error.toString()))
        }
    }
}
