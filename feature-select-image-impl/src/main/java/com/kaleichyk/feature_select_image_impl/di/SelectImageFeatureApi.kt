package com.kaleichyk.feature_select_image_impl.di

import com.kaleichyk.feature_select_image_api.SelectImageApi
import com.koleychik.module_injector.injections.BaseApi

interface SelectImageFeatureApi: BaseApi {

    fun selectImageApi(): SelectImageApi

}