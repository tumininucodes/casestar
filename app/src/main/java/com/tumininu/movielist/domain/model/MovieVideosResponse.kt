package com.tumininu.movielist.domain.model

data class MovieVideosResponse(
    var page: Int?,
    var results: List<MovieVideos>,
)
