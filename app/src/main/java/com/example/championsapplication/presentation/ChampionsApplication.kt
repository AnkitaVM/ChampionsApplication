package com.example.championsapplication.presentation

import android.app.Application
import com.example.championsapplication.BuildConfig
import com.example.championsapplication.di.ApiModule
import com.example.championsapplication.di.AppComponent
import com.example.championsapplication.di.DaggerAppComponent
import com.example.championsapplication.di.DbModule

class ChampionsApplication : Application() {

    lateinit var daggerComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        daggerComponent = DaggerAppComponent.builder()
            .apiModule(ApiModule(BuildConfig.BASE_URL))
            .dbModule(DbModule(applicationContext))
            .build()

    }
}