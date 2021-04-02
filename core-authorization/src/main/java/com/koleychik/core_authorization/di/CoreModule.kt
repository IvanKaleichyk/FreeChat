package com.koleychik.core_authorization.di

import com.koleychik.core_authorization.newapi.AccountRepository
import com.koleychik.core_authorization.newapi.AuthDbRepository
import com.koleychik.core_authorization.newapi.AuthFirebaseRepository
import com.koleychik.core_authorization.newapi.AuthRepository
import com.koleychik.core_authorization.newapiimpl.AccountRepositoryImpl
import com.koleychik.core_authorization.newapiimpl.AuthDbRepositoryImpl
import com.koleychik.core_authorization.newapiimpl.AuthFirebaseRepositoryImpl
import com.koleychik.core_authorization.newapiimpl.AuthRepositoryImpl
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

}