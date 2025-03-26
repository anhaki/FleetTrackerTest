package com.haki.fleettrackertest.core.domain.usecase

import com.haki.fleettrackertest.core.domain.repository.IFleetRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repository: IFleetRepository) {
    suspend operator fun invoke() {
        repository.logout()
    }
}