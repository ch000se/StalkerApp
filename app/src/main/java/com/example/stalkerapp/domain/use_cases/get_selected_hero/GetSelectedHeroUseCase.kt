package com.example.stalkerapp.domain.use_cases.get_selected_hero

import com.example.stalkerapp.data.repository.Repository
import com.example.stalkerapp.domain.model.Hero

class GetSelectedHeroUseCase(private val repository: Repository) {

    suspend operator fun invoke(heroId: Int): Hero{
        return repository.getSelectedHero(heroId)
    }
}