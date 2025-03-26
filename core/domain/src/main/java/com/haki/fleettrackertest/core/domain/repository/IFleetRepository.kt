package com.haki.fleettrackertest.core.domain.repository

import com.google.android.gms.maps.model.LatLng
import com.haki.fleettrackertest.core.domain.model.StatusLog
import com.haki.fleettrackertest.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IFleetRepository {
    suspend fun insertStatusLog(statusLog: StatusLog)
    fun getLastStatusLog(): Flow<StatusLog>
    fun getAllStatusLog(): Flow<List<StatusLog>>
    suspend fun getSession(): Flow<User>
    suspend fun login(user: User)
    suspend fun logout()
    suspend fun getFullRoute(): Flow<List<LatLng>>
}