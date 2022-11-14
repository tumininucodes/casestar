package com.tumininu.movielist.presentation.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.tumininu.movielist.presentation.ui.search.components.Search

@Composable
fun SearchView(modifier: Modifier = Modifier) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    Column {
        Search(textState)
        Spacer(modifier = modifier)

    }
}