package com.kaleichyk.feature_searching.di

import com.kaleichyk.core_database.api.UsersRepository
import com.kaleichyk.feature_searching.SearchingFeatureNavigationApi
import com.koleychik.module_injector.injections.BaseDependencies

interface SearchingFeatureDependencies : BaseDependencies {
    fun usersRepository(): UsersRepository
    fun navigation(): SearchingFeatureNavigationApi
}