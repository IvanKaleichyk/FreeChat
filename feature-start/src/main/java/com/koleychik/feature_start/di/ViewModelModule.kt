package com.koleychik.feature_start.di

import androidx.lifecycle.ViewModel
import com.koleychik.feature_start.ui.viewModel.StartViewModel
import com.koleychik.module_injector.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(StartViewModel::class)
    abstract fun provideViewModel(viewModel: StartViewModel): ViewModel

}