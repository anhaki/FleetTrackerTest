package com.haki.fleettrackertest.core.data.source.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.haki.fleettrackertest.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference(private val dataStore: DataStore<Preferences>) {
    suspend fun saveUserSession(user: UserEntity) {
        dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = user.username
            preferences[PASSWORD_KEY] = user.passwordHash
        }
    }

    fun getUserSession(): Flow<UserEntity> {
        return dataStore.data.map { preferences ->
            UserEntity(
                preferences[USERNAME_KEY] ?: "",
                preferences[PASSWORD_KEY] ?: "",
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val PASSWORD_KEY = stringPreferencesKey("password")
    }
}