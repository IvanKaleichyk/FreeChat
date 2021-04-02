package com.koleychik.freechat

import android.app.Application
import com.koleychik.freechat.di.AppComponent
import com.koleychik.freechat.di.modules.AppModule

class App : Application() {

    companion object {
        internal lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component =
            DaggerAppComponent.builder().appModule(AppModule(this.applicationContext)).build()
    }

}