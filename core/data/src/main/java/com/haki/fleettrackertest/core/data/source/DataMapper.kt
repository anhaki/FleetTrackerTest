package com.haki.fleettrackertest.core.data.source

import com.haki.fleettrackertest.core.data.source.local.entity.StatusLogEntity
import com.haki.fleettrackertest.core.data.source.local.entity.UserEntity
import com.haki.fleettrackertest.core.domain.model.StatusLog
import com.haki.fleettrackertest.core.domain.model.User

object DataMapper {
    fun mapEntitytoDomain(data: UserEntity) = User(
        username = data.username,
        passwordHash = data.passwordHash
    )
    fun mapDomaintoEntity(data: User) = UserEntity(
        username = data.username,
        passwordHash = data.passwordHash
    )

    fun mapEntitytoDomain(data: StatusLogEntity) = StatusLog(
        id = data.id,
        speed = data.speed,
        engineOn = data.engineOn,
        doorClosed = data.doorClosed,
        date = data.date,
    )
    fun mapDomaintoEntity(data: StatusLog) = StatusLogEntity(
        id = data.id,
        speed = data.speed,
        engineOn = data.engineOn,
        doorClosed = data.doorClosed,
        date = data.date,
    )

}