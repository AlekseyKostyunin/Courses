package com.alekseykostyunin.courses.domain.usecase

import com.alekseykostyunin.courses.domain.repository.UsersRepository

class AuthUserUseCase(private val usersRepository: UsersRepository) {
    fun invoke(email: String, password: String): Boolean {
        return usersRepository.authorization(email, password)
    }
}