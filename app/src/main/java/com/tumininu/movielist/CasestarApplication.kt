package com.tumininu.movielist

import android.app.Application
import com.tumininu.movielist.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CasestarApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CasestarApplication)
            modules(appModule)
        }
    }

}