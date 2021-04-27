package com.kaleichyk.core_database.di

import com.kaleichyk.core_database.api.DialogsRepository
import com.kaleichyk.core_database.api.MessagesRepository
import com.kaleichyk.core_database.api.UsersRepository
import com.kaleichyk.core_database.impl.DialogsRepositoryImpl
import com.kaleichyk.core_database.impl.MessagesRepositoryImpl
import com.kaleichyk.core_database.impl.UsersRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal abstract class DatabaseCoreModule {

    @Binds
    @Singleton
    abstract fun provideDialogsRepository(impl: DialogsRepositoryImpl): DialogsRepository

    @Binds
    @Singleton
    abstract fun provideMessagesRepository(impl: MessagesRepositoryImpl): MessagesRepository

    @Binds
    @Singleton
    abstract fun provideUsersRepository(impl: UsersRepositoryImpl): UsersRepository

}