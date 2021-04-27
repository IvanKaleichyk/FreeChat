package com.koleychik.feature_all_dialogs.di

import com.kaleichyk.core_database.api.DialogsRepository
import com.koleychik.feature_all_dialogs.AllDialogFeatureNavigationApi
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.module_injector.injections.BaseDependencies

interface AllDialogsFeatureDependencies : BaseDependencies {

    fun navigationApi(): AllDialogFeatureNavigationApi
    fun repository(): DialogsRepository
    fun loadingApi(): LoadingApi

}