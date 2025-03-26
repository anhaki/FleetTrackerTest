package com.haki.fleettrackertest.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haki.fleettrackertest.core.data.source.local.entity.StatusLogEntity

@Database(entities = [StatusLogEntity::class], version = 1, exportSchema = false)
abstract class FleetDatabase : RoomDatabase() {
    abstract fun statusLogDao(): StatusLogDao
}