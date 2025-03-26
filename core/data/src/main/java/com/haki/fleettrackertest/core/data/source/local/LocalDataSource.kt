package com.haki.fleettrackertest.core.data.source.local

import com.haki.fleettrackertest.core.data.source.local.entity.StatusLogEntity
import com.haki.fleettrackertest.core.data.source.local.entity.UserEntity
import com.haki.fleettrackertest.core.data.source.local.preference.UserPreference
import com.haki.fleettrackertest.core.data.source.local.room.StatusLogDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val userPreference: UserPreference,
    private val dao: StatusLogDao,
) {
    suspend fun insertStatusLog(statusLogEntity: StatusLogEntity) {
        dao.insertStatusLog(statusLogEntity)
    }
    fun getLastStatusLog(): Flow<StatusLogEntity?> {
        return dao.getLastStatusLog()
    }
    suspend fun saveUserSession(user: UserEntity) {
        userPreference.saveUserSession(user)
    }
    fun getUserSession(): Flow<UserEntity> {
        return userPreference.getUserSession()
    }
    suspend fun logout() {
        userPreference.logout()
    }
}