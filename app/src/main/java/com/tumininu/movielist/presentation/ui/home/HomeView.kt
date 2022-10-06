package com.tumininu.movielist.presentation.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeView(modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        TopAppBar {
            Text(text = "Casestar")
        }
    }) { padding ->
        Column(modifier = modifier.padding(padding)) {

        }
    }
}