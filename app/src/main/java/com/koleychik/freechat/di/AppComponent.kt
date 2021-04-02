package com.koleychik.freechat.di

import com.koleychik.freechat.MainActivity
import com.koleychik.freechat.di.modules.ApiModule
import com.koleychik.freechat.di.modules.AppModule
import com.koleychik.freechat.di.modules.DependenciesModule
import com.koleychik.freechat.di.modules.DestroyersModule
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        DependenciesModule::class,
        DestroyersModule::class,
        ApiModule::class]
)
@Singleton
interface AppComponent {

    fun inject(activity: MainActivity)

}