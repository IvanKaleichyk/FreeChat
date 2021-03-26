package com.koleychik.core_files.di

import com.koleychik.core_files.FilesRepository
import com.koleychik.core_files.FilesRepositoryImpl
import dagger.Binds

abstract class CoreModule {

    @Binds
    abstract fun provideFilesRepository(impl: FilesRepositoryImpl): FilesRepository

}