package com.tumininu.movielist.domain.model

data class MovieSearchResponse(
    var page: Int?,
    var results: List<Movie>,
)
