package com.koleychik.core_authorization.di

import com.koleychik.core_authorization.api.AccountRepository
import com.koleychik.core_authorization.api.AuthenticationRepository
import com.koleychik.core_authorization.api.FirebaseDatabaseRepository
import com.koleychik.core_authorization.impl.AccountRepositoryImpl
import com.koleychik.core_authorization.impl.AuthenticationRepositoryImpl
import com.koleychik.core_authorization.impl.FirebaseDatabaseRepositoryImpl
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
    abstract fun provideAuthenticationRepository(impl: AuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    @PerFeature
    abstract fun provideFirebaseDatabaseRepository(impl: FirebaseDatabaseRepositoryImpl): FirebaseDatabaseRepository

}