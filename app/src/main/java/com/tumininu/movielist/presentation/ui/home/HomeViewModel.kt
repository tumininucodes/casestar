package com.tumininu.movielist.presentation.ui.home

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

    private val movieRepository = MovieRepository()
    val movies: Flow<PagingData<Movie>> = Pager(PagingConfig(pageSize = 15)) {
        MovieSource(movieRepository)
    }.flow

}