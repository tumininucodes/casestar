package com.tumininu.movielist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tumininu.movielist.domain.model.*
import com.tumininu.movielist.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 15)) {
        MovieSource(movieRepository)
    }.flow
    private val movieVideos = MutableStateFlow<MovieVideosResponse?>(null)
    var currentMovie: Movie? = null

    private val castResponse = MutableStateFlow<NetworkResult<CastResponse>>(NetworkResult.Loading)

    fun getMovieVideos(movieId: String): MutableStateFlow<MovieVideosResponse?> {
        viewModelScope.launch {
            val response = movieRepository.getMovieVideos(movieId)
            if (response.isSuccessful && response.body() != null) {
                movieVideos.value = response.body()
            }
        }
        return movieVideos
    }

    fun getCast(movieId: String): MutableStateFlow<NetworkResult<CastResponse>> {
        viewModelScope.launch {
            try {
                val response = movieRepository.getCast(movieId)
                if (response.isSuccessful && response.body() != null) {
                    castResponse.emit(NetworkResult.Success(response.body()!!))
                } else {
                    castResponse.emit(NetworkResult.Error(Throwable(response.code().toString())))
                }
            } catch (e: Exception) {
                castResponse.emit(NetworkResult.Error(e))
            }
        }
        return castResponse
    }
}