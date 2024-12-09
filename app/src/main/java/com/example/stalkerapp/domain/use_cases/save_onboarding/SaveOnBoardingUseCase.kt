package com.example.stalkerapp.domain.use_cases.save_onboarding

import com.example.stalkerapp.data.repository.Repository

class SaveOnBoardingUseCase(private val repository: Repository) {
    suspend operator fun invoke(state: Boolean) {
        repository.saveOnBoardingState(state)
    }
}