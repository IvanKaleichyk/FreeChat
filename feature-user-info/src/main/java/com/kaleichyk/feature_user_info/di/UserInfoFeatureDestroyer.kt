package com.kaleichyk.feature_user_info.di

import com.koleychik.module_injector.injections.BaseDestroyer

interface UserInfoFeatureDestroyer : BaseDestroyer{

    fun destroy()

}