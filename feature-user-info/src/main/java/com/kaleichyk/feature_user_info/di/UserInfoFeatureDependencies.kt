package com.kaleichyk.feature_user_info.di

import com.kaleichyk.core_cloud_storage.framework.CloudStorageRepository
import com.kaleichyk.core_database.api.DialogsRepository
import com.kaleichyk.core_database.api.UsersRepository
import com.kaleichyk.feature_user_info.UserInfoNavigationApi
import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.module_injector.injections.BaseDependencies

interface UserInfoFeatureDependencies : BaseDependencies {

    fun usersRepository(): UsersRepository
    fun dialogsRepository(): DialogsRepository
    fun accountRepository(): AccountRepository
    fun cloudStorageRepository(): CloudStorageRepository
    fun loadingApi(): LoadingApi
    fun userInfoNavigationApi(): UserInfoNavigationApi

}