package com.example.stalkerapp.presentation.screen.home

import androidx.lifecycle.ViewModel
import com.example.stalkerapp.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    useCases: UseCases
): ViewModel() {

    val getAllHeroes = useCases.getAllHeroesUseCase()

}