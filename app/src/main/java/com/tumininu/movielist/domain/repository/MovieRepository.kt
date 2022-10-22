package com.tumininu.movielist.domain.repository

import com.tumininu.movielist.data.ApiClient
import com.tumininu.movielist.domain.model.MovieResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieRepository {

    private var _movies = MutableStateFlow<MovieResponse?>(null)
    private val movies = _movies.asStateFlow()

    suspend fun getPopularMovies(nextPage: Int): StateFlow<MovieResponse?> {
        try {
            val response = ApiClient.retrofitService.fetchMovies(page = nextPage)
            if (response.isSuccessful && response.body() != null) {
                _movies.value = response.body()!!
            }
        } catch (e: Exception) {

        }
        return movies
    }
}