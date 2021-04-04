package com.koleychik.core_authentication.impl

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.koleychik.core_authentication.api.AccountRepository
import com.koleychik.core_authentication.api.DataStoreRepository
import com.koleychik.core_authentication.dataStoreUtils.PreferencesKeys
import com.koleychik.core_authentication.dataStoreUtils.PreferencesKeys.dataStore
import com.koleychik.models.User
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DataStoreRepositoryImpl @Inject constructor(
    context: Context,
    private val accountRepository: AccountRepository
) : DataStoreRepository {

    private val dataStore = context.dataStore

    override suspend fun getUser(): User? {
        val jsonUser = dataStore.data.map {
            it[PreferencesKeys.user]
        }.firstOrNull() ?: return null
        return Gson().fromJson(jsonUser, User::class.java).apply {
            accountRepository.user = this
        }
    }

    override suspend fun saveUser(user: User) {
        val jsonUser = Gson().toJson(user)
        accountRepository.user = user
        dataStore.edit {
            it[PreferencesKeys.user] = jsonUser
        }
    }
}