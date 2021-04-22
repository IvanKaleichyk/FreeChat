package com.kaleichyk.core_database.di

import com.kaleichyk.core_database.api.DialogsRepository
import com.kaleichyk.core_database.api.MessagesRepository
import com.kaleichyk.core_database.api.UsersRepository

interface DatabaseCoreApi {

    fun dialogsRepository(): DialogsRepository
    fun messagesRepository(): MessagesRepository
    fun usersRepository(): UsersRepository

}