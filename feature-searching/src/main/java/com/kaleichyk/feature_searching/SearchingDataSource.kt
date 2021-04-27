package com.kaleichyk.feature_searching

import com.kaleichyk.core_database.api.UsersRepository
import com.koleychik.models.constants.UserConstants
import com.koleychik.models.results.user.UsersResult
import javax.inject.Inject

internal class SearchingDataSource @Inject constructor(private val usersRepository: UsersRepository) {

    fun get50LastUsers(res: (UsersResult) -> Unit) {
        usersRepository.getUsers(UserConstants.CREATED, 0, 50, res)
    }

    fun searchByName(name: String, res: (UsersResult) -> Unit) {
        usersRepository.searchByName(name, res)
    }

}