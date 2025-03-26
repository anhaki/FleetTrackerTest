package com.haki.fleettrackertest.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haki.fleettrackertest.core.data.source.local.entity.StatusLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StatusLogDao {
    @Insert(entity = StatusLogEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatusLog(statusLogEntity: StatusLogEntity)

    @Query("SELECT * FROM status_log ORDER BY date DESC")
    fun getLastStatusLog(): Flow<StatusLogEntity?>
}