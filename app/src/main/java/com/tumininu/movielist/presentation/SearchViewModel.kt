package com.tumininu.movielist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tumininu.movielist.domain.model.Movie
import com.tumininu.movielist.domain.model.MovieSearchResponse
import com.tumininu.movielist.domain.model.NetworkResult
import com.tumininu.movielist.domain.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    var currentMovie: Movie? = null

    fun searchMovie(query: String): MutableStateFlow<NetworkResult<MovieSearchResponse>> {
        val searchResult =
            MutableStateFlow<NetworkResult<MovieSearchResponse>>(NetworkResult.Loading)
        viewModelScope.launch {
            try {
                val response = movieRepository.searchMovie(query)
                if (response.isSuccessful && response.body() != null) {
                    searchResult.emit(NetworkResult.Success(response.body()!!))
                } else {
                    searchResult.emit(NetworkResult.Error(Throwable(response.code().toString())))
                }
            } catch (e: Exception) {
                searchResult.emit(NetworkResult.Error(e))
            }
        }
        return searchResult
    }

}