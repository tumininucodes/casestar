package com.tumininu.movielist.domain.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tumininu.movielist.domain.repository.MovieRepository

class MovieSource(
    private val movieRepository: MovieRepository
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val movieListResponse = movieRepository.getPopularMovies(nextPage)

            LoadResult.Page(
                data = (movieListResponse.value as NetworkResult<MovieResponse>),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = movieListResponse.page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }
}