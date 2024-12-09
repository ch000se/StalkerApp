package com.example.stalkerapp.presentation.screen.splash

import android.content.res.Configuration
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.stalkerapp.R
import com.example.stalkerapp.navigation.Screen
import com.example.stalkerapp.ui.theme.StalkerBlack
import com.example.stalkerapp.ui.theme.StalkerGreenDark
import com.example.stalkerapp.ui.theme.StalkerGreenLight
import com.example.stalkerapp.ui.theme.StalkerWhite

@Composable
fun SplashScreen(
    navController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    val onBoardingCompleted by splashViewModel.onBoardingCompleted.collectAsState()

    val degrees = remember { androidx.compose.animation.core.Animatable(0f) }
    LaunchedEffect(key1 =  true) {
        degrees.animateTo(
            targetValue = 360f,
            animationSpec = tween(durationMillis = 1000, delayMillis = 200)
        )
        navController.popBackStack()
        if (onBoardingCompleted) {
            navController.navigate(Screen.Home.route)
        } else {
            navController.navigate(Screen.Welcome.route)
        }
    }

    Splash(degrees.value)
}

@Composable
fun Splash(degrees: Float) {
    if(isSystemInDarkTheme()){
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(StalkerGreenDark, StalkerBlack)
                    )
                )
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.rotate(degrees),
                painter = painterResource(id = R.drawable.nuclear__1_),
                contentDescription = stringResource(R.string.stalker_logo)
            )
        }
    }else{
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(StalkerGreenLight, StalkerWhite)
                    )
                )
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.rotate(degrees),
                painter = painterResource(id = R.drawable.nuclear__1_),
                contentDescription = stringResource(R.string.stalker_logo)
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    Splash(0f)
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SplashScreenPreviewDark() {
    Splash(0f)
}

