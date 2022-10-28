package com.tumininu.movielist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.tumininu.movielist.presentation.ui.MainViewModel
import com.tumininu.movielist.presentation.ui.home.HomeView
import com.tumininu.movielist.presentation.ui.theme.MovieListTheme

class ShellActivity : ComponentActivity() {
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            viewModel = remember { MainViewModel() }
            MovieListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    HomeView(viewModel = viewModel, activity = this)
                }
            }
        }
    }

    override fun onBackPressed() {
//        if (viewModel.navigateToAboutMovie.value) {
//            viewModel.navigateToAboutMovie.value = false
//        } else {
        super.onBackPressed()
//        }

    }
}