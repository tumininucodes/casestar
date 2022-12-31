package com.tumininu.movielist.di

import com.tumininu.movielist.domain.repository.MovieRepository
import com.tumininu.movielist.presentation.MainViewModel
import com.tumininu.movielist.presentation.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { MovieRepository() }
    viewModel { MainViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}