package com.foxy.movies

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this
    }

    companion object {
        private lateinit var INSTANCE : App

        @JvmStatic
        fun get() : App = INSTANCE
    }
}