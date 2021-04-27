package com.koleychik.feature_all_dialogs.di.modules

import com.koleychik.feature_all_dialogs.AllDialogFeatureNavigationApi
import com.koleychik.feature_all_dialogs.ui.adapter.AllDialogsAdapter
import dagger.Module
import dagger.Provides

@Module
internal class AllDialogsFeatureModule {

    @Provides
    fun provideAllDialogsAdapter(navigationApi: AllDialogFeatureNavigationApi) =
        AllDialogsAdapter(navigationApi)
}