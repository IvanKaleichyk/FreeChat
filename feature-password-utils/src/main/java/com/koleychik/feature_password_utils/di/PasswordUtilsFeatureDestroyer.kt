package com.koleychik.feature_password_utils.di

import com.koleychik.module_injector.injections.BaseDestroyer

interface PasswordUtilsFeatureDestroyer : BaseDestroyer {

    fun destroy()

}