package com.kaleichyk.feature_messages.di

import com.kaleichyk.feature_messages.ui.MessagesFragment
import com.koleychik.module_injector.annotations.general.PerFeature
import dagger.Component

@Component(
    modules = [ViewModelModule::class, FeatureModule::class],
    dependencies = [MessagesFeatureDependencies::class]
)
@PerFeature
internal interface MessagesFeatureComponent : MessagesFeatureApi {

    fun inject(fragment: MessagesFragment)
}