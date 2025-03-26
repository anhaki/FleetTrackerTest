package com.haki.fleettrackertest.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "status_log")
data class StatusLogEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "speed")
    var speed: Int,

    @ColumnInfo(name = "engineOn")
    var engineOn: Boolean,

    @ColumnInfo(name = "doorClosed")
    var doorClosed: Boolean,

    @ColumnInfo(name = "date")
    var date: Long,
)
