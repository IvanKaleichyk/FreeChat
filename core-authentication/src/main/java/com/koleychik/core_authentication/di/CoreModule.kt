package com.koleychik.core_authentication.di

import com.koleychik.core_authentication.api.*
import com.koleychik.core_authentication.impl.*
import com.koleychik.module_injector.annotations.general.PerFeature
import dagger.Binds
import dagger.Module

@Module
internal abstract class CoreModule {

    @Binds
    @PerFeature
    abstract fun provideAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    @PerFeature
    abstract fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @PerFeature
    abstract fun provideAuthDbRepository(impl: AuthDbRepositoryImpl): AuthDbRepository

    @Binds
    @PerFeature
    abstract fun provideAuthFirebaseRepository(impl: AuthFirebaseRepositoryImpl): AuthFirebaseRepository

    @Binds
    @PerFeature
    abstract fun provideDataStoreRepository(impl: DataStoreRepositoryImpl): DataStoreRepository

}