package com.koleychik.core_files.di

import com.koleychik.core_files.FilesRepository
import com.koleychik.core_files.FilesRepositoryImpl
import com.koleychik.module_injector.annotations.general.PerFeature
import dagger.Binds

abstract class CoreModule {

    @Binds
    @PerFeature
    abstract fun provideFilesRepository(impl: FilesRepositoryImpl): FilesRepository

}