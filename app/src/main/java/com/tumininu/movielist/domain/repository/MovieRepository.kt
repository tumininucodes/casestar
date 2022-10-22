package com.tumininu.movielist.domain.repository

import com.tumininu.movielist.data.ApiClient
import com.tumininu.movielist.domain.model.MovieResponse
import retrofit2.Response

class MovieRepository {

    suspend fun getPopularMovies(nextPage: Int): Response<MovieResponse> {
        return ApiClient.retrofitService.fetchMovies(page = nextPage)
    }
}