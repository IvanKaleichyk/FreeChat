package com.kaleichyk.feature_searching.di.models

import com.kaleichyk.feature_searching.SearchingFeatureNavigationApi
import com.kaleichyk.feature_searching.ui.adapter.SearchingAdapter
import dagger.Module
import dagger.Provides

@Module
internal class SearchingFeatureModule {

    @Provides
    fun provideSearchingAdapter(api: SearchingFeatureNavigationApi) = SearchingAdapter(api)
}