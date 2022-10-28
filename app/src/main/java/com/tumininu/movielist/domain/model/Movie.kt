package com.tumininu.movielist.domain.model

data class Movie(
    var id: Int = 0,
    var title: String = "",
    var overview: String?,
    var poster_path: String?,
    var release_date: String?,
    var vote_count: Int = 0,
) : java.io.Serializable
