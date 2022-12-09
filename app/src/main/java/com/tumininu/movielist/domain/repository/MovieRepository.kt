package com.tumininu.movielist.domain.repository

import com.tumininu.movielist.data.ApiClient
import com.tumininu.movielist.domain.model.CastResponse
import com.tumininu.movielist.domain.model.MovieResponse
import com.tumininu.movielist.domain.model.MovieSearchResponse
import com.tumininu.movielist.domain.model.MovieVideosResponse
import retrofit2.Response

class MovieRepository {

    suspend fun getPopularMovies(nextPage: Int): Response<MovieResponse> {
        return ApiClient.retrofitService.fetchMovies(page = nextPage)
    }

    suspend fun getMovieVideos(movieId: String): Response<MovieVideosResponse> {
        return ApiClient.retrofitService.fetchMovieVideos(movieId = movieId)
    }

    suspend fun searchMovie(query: String): Response<MovieSearchResponse> {
        return ApiClient.retrofitService.searchMovie(query = query)
    }

    suspend fun getCast(movieId: String): Response<CastResponse> {
        return ApiClient.retrofitService.fetchCast(movieId = movieId)
    }

}