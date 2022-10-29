package com.tumininu.movielist.domain.model

data class Movie(
    var id: Int = 0,
    var title: String = "",
    var overview: String?,
    var poster_path: String?,
    var release_date: String?,
    var original_language: String?,
    var vote_count: Int = 0,
    var vote_average: Double = 0.0,
    var popularity: Double = 0.0,
) : java.io.Serializable
