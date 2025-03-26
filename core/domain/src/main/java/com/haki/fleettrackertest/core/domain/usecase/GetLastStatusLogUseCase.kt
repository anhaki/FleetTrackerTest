package com.haki.fleettrackertest.core.domain.usecase

import com.haki.fleettrackertest.core.domain.model.StatusLog
import com.haki.fleettrackertest.core.domain.repository.IFleetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLastStatusLogUseCase @Inject constructor(private val repository: IFleetRepository) {
     operator fun invoke(): Flow<StatusLog> = repository.getLastStatusLog()
}