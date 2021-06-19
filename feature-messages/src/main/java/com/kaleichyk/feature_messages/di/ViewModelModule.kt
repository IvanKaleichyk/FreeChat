package com.kaleichyk.feature_messages.di

import androidx.lifecycle.ViewModel
import com.kaleichyk.feature_messages.ui.viewModel.MessagesViewModel
import com.koleychik.module_injector.annotations.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MessagesViewModel::class)
    abstract fun provideMessagesViewModel(viewModel: MessagesViewModel): ViewModel

}