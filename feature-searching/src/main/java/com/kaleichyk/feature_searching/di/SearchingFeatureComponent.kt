package com.kaleichyk.feature_searching.di

import com.kaleichyk.feature_searching.di.models.SearchingFeatureModule
import com.kaleichyk.feature_searching.di.models.ViewModelModule
import com.kaleichyk.feature_searching.ui.SearchingFragment
import dagger.Component

@Component(
    modules = [SearchingFeatureModule::class, ViewModelModule::class],
    dependencies = [SearchingFeatureDependencies::class]
)
interface SearchingFeatureComponent : SearchingFeatureApi {

    fun inject(fragment: SearchingFragment)

}