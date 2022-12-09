package com.tumininu.movielist.presentation.ui.aboutMovie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tumininu.movielist.domain.model.CastResponse
import com.tumininu.movielist.domain.model.NetworkResult

@Composable
fun CastView(data: MutableState<NetworkResult<CastResponse>>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()) {
        when (data.value) {
            is NetworkResult.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResult.Success -> {
                LazyRow(content = {
                    items(items = (data.value as NetworkResult.Success<CastResponse>).data.cast) {
                        Text(text = it.name.toString())
                    }
                })
            }
            is NetworkResult.Error -> {
                Text(text = ((data.value as NetworkResult.Error).error.toString()))
            }
        }

    }
}
