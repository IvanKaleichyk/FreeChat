package com.koleychik.freechat

import android.app.Application
import com.kaleichyk.utils.CurrentUser
import com.kaleichyk.utils.StringConverter
import com.koleychik.freechat.di.AppComponent
import com.koleychik.freechat.di.DaggerAppComponent
import com.koleychik.freechat.di.modules.AppModule
import com.koleychik.freechat.managers.MainManager
import javax.inject.Inject

class App : Application() {

    @Inject
    internal lateinit var navigator: Navigator

    @Inject
    internal lateinit var manager: MainManager

    companion object {
        internal lateinit var component: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        StringConverter.init(this)
        component =
            DaggerAppComponent.builder().appModule(AppModule(this.applicationContext)).build()
                .apply {
                    inject(this@App)
                }
        initWhenSetUser()
    }

    private fun initWhenSetUser() {
        CurrentUser.whenSetUser = { lastUser, newUser ->
            if (lastUser != null) manager.unsubscribeFromUserNotification(lastUser.id)

            if (newUser != null) manager.subscribeToUserNotification(newUser.id)
        }
    }

}