package com.haki.fleettrackertest.core.domain.usecase

import com.haki.fleettrackertest.core.domain.model.User
import com.haki.fleettrackertest.core.domain.repository.IFleetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: IFleetRepository) {
    private val users = listOf(
        User(username = "username", passwordHash = hashPassword("pass123"))
    )

    suspend operator fun invoke(username: String, password: String): Boolean {
        val user = users.find { it.username == username } ?: return false
        if (user.passwordHash == hashPassword(password)) {
            repository.login(
                User(username, user.passwordHash)
            )
            return true
        }
        return false
    }

    private fun hashPassword(password: String): String {
        return password.reversed()
    }
}