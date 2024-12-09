package com.example.stalkerapp.presentation.screen.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.example.stalkerapp.presentation.common.ListContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.stalkerapp.ui.theme.descriptionColor
import com.example.stalkerapp.ui.theme.welcomeScreenBackground

@ExperimentalCoilApi
@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by searchViewModel.searchQuery
    val heroes = searchViewModel.searchedHeroes.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            SearchTopBar(
                text = searchQuery,
                onTextChange = {
                    searchViewModel.updateSearchQuery(it)
                },
                onSearchClicked = {
                    searchViewModel.searchHeroes(it)
                },
                onCloseClicked = {
                    navController.popBackStack()
                }
            )
        },
        content = {
            // Add background color to the content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(welcomeScreenBackground) // Set background color dynamically
            ) {
                ListContent(
                    heroes = heroes,
                    navController = navController,
                )
            }
        }
    )
}
