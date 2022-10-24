package com.tumininu.movielist.presentation.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tumininu.movielist.domain.model.Movie
import com.tumininu.movielist.domain.model.MovieSource
import com.tumininu.movielist.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {

    private val movieRepository = MovieRepository()
    val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 15)) {
        MovieSource(movieRepository)
    }.flow

    val appBarTitle: MutableState<String>
        get() {
            return if (navigateToAboutMovie.value) {
                mutableStateOf(currentMovie?.title.toString())
            } else {
                mutableStateOf("Casestar")
            }
        }

    var navigateToAboutMovie = mutableStateOf(false)

    var currentMovie: Movie? = null
}