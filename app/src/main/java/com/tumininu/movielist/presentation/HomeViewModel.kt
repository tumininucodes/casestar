package com.tumininu.movielist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tumininu.movielist.data.ApiClient
import com.tumininu.movielist.domain.model.Movie
import com.tumininu.movielist.domain.model.MovieResponse
import com.tumininu.movielist.domain.model.MovieSource
import com.tumininu.movielist.domain.model.NetworkResult
import com.tumininu.movielist.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

//    private var _movies = MutableStateFlow<NetworkResult<MovieResponse>>(NetworkResult.Loading)
//    private val movies = _movies.asStateFlow()
//    val moviesList = mutableListOf<Movie>()


    val movieRepository = MovieRepository()
    val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 20)) {
        MovieSource(movieRepository)
    }.flow


//
//    init {
//        fetchMovies()
//    }
//
//    fun fetchMovies(page: Int = 1) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                _movies.value = NetworkResult.Loading
//                val response = ApiClient.retrofitService.fetchMovies(page = page)
//                if (response.isSuccessful && response.body() != null) {
//                    _movies.value = NetworkResult.Success(response.body()!!)
//                    moviesList.addAll(response.body()!!.results)
//                } else {
//                    _movies.value =
//                        NetworkResult.Error(Throwable("Something went wrong. Please try again"))
//                }
//            } catch (e: Exception) {
//                _movies.value = NetworkResult.Error(Throwable("Error fetching movies"))
//            }
//        }
//    }
//
//    fun fetchMoreMovies(page: Int) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                _movies.value = NetworkResult.Loading
//                val response = ApiClient.retrofitService.fetchMovies(page = page)
//                if (response.isSuccessful && response.body() != null) {
//                    _movies.value = NetworkResult.Success(response.body()!!)
//                } else {
//                    _movies.value =
//                        NetworkResult.Error(Throwable("Something went wrong. Please try again"))
//                }
//            } catch (e: Exception) {
//                _movies.value = NetworkResult.Error(Throwable("Error fetching movies"))
//            }
//        }
//
//    }
//
//    fun getMovies(): StateFlow<NetworkResult<MovieResponse>> {
//        return movies
//    }

}