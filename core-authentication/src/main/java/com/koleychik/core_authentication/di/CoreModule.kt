package com.koleychik.core_authentication.di

import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.core_authentication.api.AuthDbDataSource
import com.koleychik.core_authentication.api.AuthFirebaseDataSource
import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.core_authentication.impl.AccountRepositoryImpl
import com.koleychik.core_authentication.impl.AuthDbDataSourceImpl
import com.koleychik.core_authentication.impl.AuthFirebaseDataSourceImpl
import com.koleychik.core_authentication.impl.AuthRepositoryImpl
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
    abstract fun provideAuthDbRepository(impl: AuthDbDataSourceImpl): AuthDbDataSource

    @Binds
    @PerFeature
    abstract fun provideAuthFirebaseRepository(impl: AuthFirebaseDataSourceImpl): AuthFirebaseDataSource
}