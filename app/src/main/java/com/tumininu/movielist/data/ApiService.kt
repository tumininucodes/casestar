package com.tumininu.movielist.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tumininu.movielist.BuildConfig
import com.tumininu.movielist.domain.model.CastResponse
import com.tumininu.movielist.domain.model.MovieResponse
import com.tumininu.movielist.domain.model.MovieSearchResponse
import com.tumininu.movielist.domain.model.MovieVideosResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun fetchMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
    ): Response<MovieResponse>

    @GET("movie/{movieId}/videos")
    suspend fun fetchMovieVideos(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
    ): Response<MovieVideosResponse>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("query") query: String,
        @Query("include_adult") include_adult: Boolean = false,
    ): Response<MovieSearchResponse>

    @GET("movie/{movieId}/credits")
    suspend fun fetchCast(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US",
    ): Response<CastResponse>

}

object ApiClient {

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofitService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        retrofit.create(ApiService::class.java)
    }
}