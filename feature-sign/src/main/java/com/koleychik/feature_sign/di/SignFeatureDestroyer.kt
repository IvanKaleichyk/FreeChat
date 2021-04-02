package com.koleychik.feature_sign.di

import com.koleychik.module_injector.injections.BaseDestroyer

interface SignFeatureDestroyer : BaseDestroyer {

    fun destroy()

}