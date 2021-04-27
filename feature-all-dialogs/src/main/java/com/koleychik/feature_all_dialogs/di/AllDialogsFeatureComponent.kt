package com.koleychik.feature_all_dialogs.di

import com.koleychik.feature_all_dialogs.di.modules.AllDialogsFeatureModule
import com.koleychik.feature_all_dialogs.di.modules.ViewModelModule
import com.koleychik.feature_all_dialogs.ui.AllDialogsFragment
import dagger.Component

@Component(
    modules = [AllDialogsFeatureModule::class, ViewModelModule::class],
    dependencies = [AllDialogsFeatureDependencies::class]
)
interface AllDialogsFeatureComponent : AllDialogsFeatureApi{

    fun inject(fragment: AllDialogsFragment)

}