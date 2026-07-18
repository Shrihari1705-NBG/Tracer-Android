package com.shrihari.smartcampusnavigator.data.repository

import com.shrihari.smartcampusnavigator.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor() : HomeRepository {
    override suspend fun getWelcomeMessage(): String {
        return "Ready to navigate your campus?"
    }
}
