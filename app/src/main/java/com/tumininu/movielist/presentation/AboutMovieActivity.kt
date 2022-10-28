package com.tumininu.movielist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import com.tumininu.movielist.R
import com.tumininu.movielist.domain.model.Movie
import com.tumininu.movielist.presentation.ui.aboutMovie.AboutMovie
import com.tumininu.movielist.presentation.ui.theme.MovieListTheme

class AboutMovieActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_movie)

        val movie = intent.getSerializableExtra("movie") as Movie


        val aboutView = findViewById<ComposeView>(R.id.composable)
        aboutView.setContent {
            MovieListTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    AboutMovie(movie = movie)
                }
            }
        }
    }
}