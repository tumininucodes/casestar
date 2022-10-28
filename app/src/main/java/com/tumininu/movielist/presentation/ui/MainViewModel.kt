package com.tumininu.movielist.presentation.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tumininu.movielist.domain.model.Movie
import com.tumininu.movielist.domain.model.MovieSource
import com.tumininu.movielist.domain.model.MovieVideosResponse
import com.tumininu.movielist.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val movieRepository = MovieRepository()
    val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 15)) {
        MovieSource(movieRepository)
    }.flow
    val movieVideos = MutableStateFlow<MovieVideosResponse?>(null)

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

    fun getMovieVideos(movieId: String): MutableStateFlow<MovieVideosResponse?> {
        viewModelScope.launch {
            val response = movieRepository.getMovieVideos(movieId)
            if (response.isSuccessful && response.body() != null) {
                movieVideos.value = response.body()
            }
        }
        return movieVideos
    }
}