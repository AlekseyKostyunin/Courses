package com.alekseykostyunin.courses.data.repository

import com.alekseykostyunin.courses.domain.repository.UsersRepository

object UsersRepositoryImpl: UsersRepository {
    override fun getUsers() {
        TODO("Not yet implemented")
    }

    override fun authorization(email: String, password: String): Boolean {
       return true
    }

}