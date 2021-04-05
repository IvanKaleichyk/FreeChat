package com.koleychik.feature_password_utils.di

import androidx.lifecycle.ViewModel
import com.koleychik.feature_password_utils.viewModels.SpecifyEmailViewModel
import com.koleychik.module_injector.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SpecifyEmailViewModel::class)
    abstract fun provideSpecifyEmailViewModel(impl: SpecifyEmailViewModel): ViewModel

}