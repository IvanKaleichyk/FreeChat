package com.koleychik.feature_sign.di

import androidx.lifecycle.ViewModel
import com.koleychik.feature_sign.ui.viewModels.SignUpViewModel
import com.koleychik.module_injector.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun provideSignUpViewModel(impl: SignUpViewModel): ViewModel

}