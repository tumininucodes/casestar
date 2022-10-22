package com.tumininu.movielist.domain.model

data class MovieResponse(
    var page: Int?,
    var results: List<Movie>
)
