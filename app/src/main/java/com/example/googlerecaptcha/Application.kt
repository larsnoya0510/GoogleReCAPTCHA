package com.example.googlerecaptcha

import android.app.Activity
import android.content.res.Configuration
class Application : android.app.Application() {
    companion object {
        lateinit var instance: Application private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}