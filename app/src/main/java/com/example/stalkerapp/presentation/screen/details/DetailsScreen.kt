package com.example.stalkerapp.presentation.screen.details

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.stalkerapp.presentation.screens.details.DetailsContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavHostController,
    detailsViewModel: DetailsViewModel = hiltViewModel()
    ) {
    val selectedHero by detailsViewModel.selectedHero.collectAsState()

    DetailsContent(
        navController = navController,
        selectedHero = selectedHero
    )
}