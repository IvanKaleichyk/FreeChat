package com.koleychik.feature_loading_api

import com.koleychik.module_injector.injections.BaseApi

interface LoadingFeatureApi: BaseApi {

    fun loadingApi(): LoadingApi

}