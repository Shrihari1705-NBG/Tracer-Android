package com.shrihari.smartcampusnavigator.domain.repository

interface HomeRepository {
    suspend fun getWelcomeMessage(): String
}
