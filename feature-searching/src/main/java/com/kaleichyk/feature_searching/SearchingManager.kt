package com.kaleichyk.feature_searching

import com.kaleichyk.core_database.api.UsersRepository
import com.koleychik.models.constants.UserConstants
import javax.inject.Inject

internal class SearchingManager @Inject constructor(private val usersRepository: UsersRepository) {

    suspend fun get50LastUsers() = usersRepository.getUsers(UserConstants.CREATED, 1, 50)

    suspend fun searchByName(name: String) =
        usersRepository.searchByName(name)


}