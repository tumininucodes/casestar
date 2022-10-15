package com.tumininu.movielist.model

data class MovieResponse(
    var page: Int?,
    var results: List<Movie>
)
