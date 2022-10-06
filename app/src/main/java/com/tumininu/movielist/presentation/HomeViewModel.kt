package com.tumininu.movielist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tumininu.movielist.data.ApiClient
import com.tumininu.movielist.model.Movie
import com.tumininu.movielist.model.MovieResponse
import com.tumininu.movielist.model.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private var _movies = MutableLiveData<NetworkResult<MovieResponse>>()
    private val movies: LiveData<NetworkResult<MovieResponse>> = _movies
    val moviesList = mutableListOf<Movie>()

    init {
        fetchMovies()
    }

    fun fetchMovies(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _movies.postValue(NetworkResult.Loading)
                val response = ApiClient.retrofitService.fetchMovies(page = page)
                if (response.isSuccessful && response.body() != null) {
                    _movies.postValue(NetworkResult.Success(response.body()!!))
                } else {
                    _movies.postValue(NetworkResult.Error(Throwable("Something went wrong. Please try again")))
                }
            } catch (e: Exception) {
                _movies.postValue(NetworkResult.Error(Throwable("Error fetching movies")))
            }
        }
    }

    fun getMovies(): LiveData<NetworkResult<MovieResponse>> {
        return movies
    }

}