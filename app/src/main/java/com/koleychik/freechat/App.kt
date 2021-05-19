package com.koleychik.freechat

import android.app.Application
import com.kaleichyk.utils.StringConverter
import com.koleychik.freechat.di.AppComponent
import com.koleychik.freechat.di.DaggerAppComponent
import com.koleychik.freechat.di.modules.AppModule
import javax.inject.Inject

class App : Application() {

    @Inject
    internal lateinit var navigator: Navigator

    companion object {
        internal lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        StringConverter.init(this)
        component =
            DaggerAppComponent.builder().appModule(AppModule(this.applicationContext)).build()
                .apply {
                    inject(this@App)
                }
    }


}