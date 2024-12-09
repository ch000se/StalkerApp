package com.example.stalkerapp.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.example.stalkerapp.navigation.Screen
import com.example.stalkerapp.presentation.common.ListContent
import com.example.stalkerapp.ui.theme.welcomeScreenBackground

@ExperimentalCoilApi
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val allHeroes = homeViewModel.getAllHeroes.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            HomeTopBar(onSearchedClick = {
                navController.navigate(Screen.Search.route)
            })
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(welcomeScreenBackground) // Використовується фон із теми
                    .padding(paddingValues)
            ) {
                ListContent(
                    heroes = allHeroes,
                    navController = navController
                )
            }
        }
    )
}
