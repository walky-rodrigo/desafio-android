package com.picpay.desafio.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DesafioAndroidApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: DesafioAndroidApplication
            private set
    }
}