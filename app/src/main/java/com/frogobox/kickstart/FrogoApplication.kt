package com.frogobox.kickstart

import android.app.Application
import android.content.Context
import com.frogobox.kickstart.di.repositoryModule
import com.frogobox.kickstart.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/*
 * Created by Faisal Amir on 23/10/2020
 * KickStartProject Source Code
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * -----------------------------------------
 * Copyright (C) 2020 FrogoBox Inc.      
 * All rights reserved
 *
 */

class FrogoApplication : Application() {

    companion object {
        lateinit var instance: FrogoApplication
        fun getContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@FrogoApplication)
            androidLogger(Level.NONE)
            modules(listOf(repositoryModule, viewModelModule))
        }
    }

}