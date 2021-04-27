package com.koleychik.feature_all_dialogs.di.modules

import androidx.lifecycle.ViewModel
import com.koleychik.feature_all_dialogs.ui.viewModels.AllDialogsViewModel
import com.koleychik.module_injector.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AllDialogsViewModel::class)
    abstract fun provideDialogsViewModel(viewModel: AllDialogsViewModel): ViewModel

}