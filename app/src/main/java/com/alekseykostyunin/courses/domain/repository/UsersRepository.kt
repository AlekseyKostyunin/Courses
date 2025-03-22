package com.alekseykostyunin.courses.domain.repository

interface UsersRepository {
    fun getUsers()
    fun authorization(email: String, password: String): Boolean
}