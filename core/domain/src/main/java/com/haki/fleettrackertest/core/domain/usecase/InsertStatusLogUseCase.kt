package com.haki.fleettrackertest.core.domain.usecase

import com.haki.fleettrackertest.core.domain.model.StatusLog
import com.haki.fleettrackertest.core.domain.model.User
import com.haki.fleettrackertest.core.domain.repository.IFleetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertStatusLogUseCase @Inject constructor(private val repository: IFleetRepository) {
    suspend operator fun invoke(statusLog: StatusLog) {
        repository.insertStatusLog(statusLog)
    }
}