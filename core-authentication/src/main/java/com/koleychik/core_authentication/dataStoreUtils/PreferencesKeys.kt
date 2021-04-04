package com.koleychik.core_authentication.dataStoreUtils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


object PreferencesKeys {
    private const val name = "USER_DATA_STORE"
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name)
    val user = stringPreferencesKey("user")
}