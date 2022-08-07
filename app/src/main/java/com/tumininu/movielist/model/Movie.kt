package com.tumininu.movielist.model

data class Movie(
    var id: Int = 0,
    var title: String = "",
    var overview: String?,
    var release_date: String?,
    var vote_count: Int = 0
)
