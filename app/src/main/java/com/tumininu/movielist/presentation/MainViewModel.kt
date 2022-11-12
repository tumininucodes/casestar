package com.tumininu.movielist.presentation

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
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val movieRepository = MovieRepository()
    val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 15)) {
        MovieSource(movieRepository)
    }.flow
    private val movieVideos = MutableStateFlow<MovieVideosResponse?>(null)

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