package com.kaleichyk.feature_user_info.di

import androidx.lifecycle.ViewModel
import com.kaleichyk.feature_user_info.ui.viewModel.UserInfoViewModel
import com.koleychik.module_injector.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
internal abstract class UserInfoFeatureModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserInfoViewModel::class)
    abstract fun provideUserInfoViewModel(impl: UserInfoViewModel): ViewModel

}