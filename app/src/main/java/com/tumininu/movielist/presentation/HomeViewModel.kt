package com.tumininu.movielist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tumininu.movielist.data.ApiClient
import com.tumininu.movielist.model.Movie
import com.tumininu.movielist.model.MovieResponse
import com.tumininu.movielist.model.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var _movies = MutableStateFlow<NetworkResult<MovieResponse>>(NetworkResult.Loading)
    private val movies = _movies.asStateFlow()
    val moviesList = mutableListOf<Movie>()

    init {
        fetchMovies()
    }

    fun fetchMovies(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _movies.value = NetworkResult.Loading
                val response = ApiClient.retrofitService.fetchMovies(page = page)
                if (response.isSuccessful && response.body() != null) {
                    _movies.value = NetworkResult.Success(response.body()!!)
                } else {
                    _movies.value =
                        NetworkResult.Error(Throwable("Something went wrong. Please try again"))
                }
            } catch (e: Exception) {
                _movies.value = NetworkResult.Error(Throwable("Error fetching movies"))
            }
        }
    }

    fun getMovies(): StateFlow<NetworkResult<MovieResponse>> {
        return movies
    }

}