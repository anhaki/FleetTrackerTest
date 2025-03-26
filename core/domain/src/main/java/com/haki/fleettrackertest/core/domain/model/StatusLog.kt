package com.haki.fleettrackertest.core.domain.model

data class StatusLog(
    var id: Int = 0,
    var speed: Int = 0,
    var engineOn: Boolean = false,
    var doorClosed: Boolean = false,
    var date: Long = 0L,
)
