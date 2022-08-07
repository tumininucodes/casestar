package com.tumininu.movielist

data class Movie(
    var title: String = "",
    var overview: String?,
    var release_date: String?,
    var vote_count: Int = 0
)
