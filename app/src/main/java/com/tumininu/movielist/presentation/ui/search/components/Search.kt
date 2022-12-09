package com.tumininu.movielist.presentation.ui.search.components

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tumininu.movielist.domain.model.MovieSearchResponse
import com.tumininu.movielist.domain.model.NetworkResult
import com.tumininu.movielist.presentation.SearchViewModel
import com.tumininu.movielist.presentation.ui.theme.Dark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Search(
    activity: Activity,
    state: MutableState<TextFieldValue>,
    viewModel: SearchViewModel,
    data: MutableState<NetworkResult<MovieSearchResponse>>,
) {
    val focusRequester = remember { FocusRequester() }

    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.searchMovie(state.value.text).collect { response ->
                    when (response) {
                        is NetworkResult.Loading -> {
                            data.value = response
                        }
                        is NetworkResult.Success -> {
                            data.value = response
                        }
                        is NetworkResult.Error -> {
                            data.value = response
                        }
                    }
                }
            }

        },
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
                    .clickable {
                        activity.finish()
                    }
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = Dark,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(text = "Search movies")
        }
    )

    LaunchedEffect(Dispatchers.Main) {
        focusRequester.requestFocus()
    }
}