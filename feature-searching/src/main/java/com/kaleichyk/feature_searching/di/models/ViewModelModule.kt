package com.kaleichyk.feature_searching.di.models

import androidx.lifecycle.ViewModel
import com.kaleichyk.feature_searching.ui.viewModel.SearchingViewModel
import com.koleychik.module_injector.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchingViewModel::class)
    abstract fun provideSearchingViewModel(impl: SearchingViewModel): ViewModel

}