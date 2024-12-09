    package com.example.stalkerapp.presentation.screen.welcome

    import android.provider.CalendarContract
    import androidx.compose.animation.AnimatedVisibility
    import androidx.compose.foundation.ExperimentalFoundationApi
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.isSystemInDarkTheme
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.size
    import androidx.compose.foundation.pager.HorizontalPager
    import androidx.compose.foundation.pager.PagerState
    import androidx.compose.foundation.pager.rememberPagerState
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.shadow
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.res.stringResource
    import androidx.compose.ui.text.font.FontFamily
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.Dp
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.hilt.navigation.compose.hiltViewModel
    import androidx.navigation.NavHostController
    import com.example.stalkerapp.R
    import com.example.stalkerapp.domain.model.OnBoardingPage
    import com.example.stalkerapp.navigation.Screen
    import com.example.stalkerapp.ui.theme.StalkerBlack
    import com.example.stalkerapp.ui.theme.StalkerGrayDark
    import com.example.stalkerapp.ui.theme.StalkerGrayLight
    import com.example.stalkerapp.ui.theme.StalkerGreenLight
    import com.example.stalkerapp.ui.theme.StalkerWhite
    import com.example.stalkerapp.ui.theme.StalkerYellowLight
    import com.example.stalkerapp.ui.theme.descriptionColor
    import com.example.stalkerapp.ui.theme.titleColor
    import com.example.stalkerapp.ui.theme.welcomeScreenBackground
    import com.example.stalkerapp.util.Constants.LAST_ON_BOARDING_PAGE
    import com.example.stalkerapp.util.Constants.ON_BOARDING_PAGE_COUNT

    @ExperimentalFoundationApi
    @Composable
    fun WelcomeScreen(
        navController: NavHostController,
        welcomeViewModel: WelcomeViewModel = hiltViewModel()
    ) {
        val pages = listOf(
            OnBoardingPage.First,
            OnBoardingPage.Second,
            OnBoardingPage.Third
        )

        val pagerState = rememberPagerState(pageCount = {ON_BOARDING_PAGE_COUNT} )

        Column(modifier = Modifier
            .fillMaxSize()
            .background(welcomeScreenBackground)){
            HorizontalPager(modifier = Modifier.weight(10f), state = pagerState, verticalAlignment = Alignment.Top)
            { position ->
                PagerScreen(pages[position])
            }
            CustomHorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 124.dp)
            )
            FinishButton(pagerState = pagerState) {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
                welcomeViewModel.saveOnBoardingState(true)
            }
        }

    }

    @Composable
    fun PagerScreen(page: OnBoardingPage) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 96.dp, start = 24.dp, end = 24.dp)
            , horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = page.image),
                contentDescription = stringResource(R.string.on_boarding_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(bottom = 16.dp),
                alignment = Alignment.Center
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = StalkerBlack.copy(alpha = 0.7f),
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        text = page.title,
                        color = titleColor,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = page.description,
                        color = descriptionColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun FinishButton(
        pagerState: PagerState,
        onClick: () -> Unit
    ) {
        val buttonColor = titleColor
        val contentColor = if (isSystemInDarkTheme()) StalkerBlack else StalkerWhite

        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                modifier = Modifier
                    .padding(bottom = 48.dp),
                visible = pagerState.currentPage == LAST_ON_BOARDING_PAGE
            ) {
                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor,
                        contentColor = contentColor
                    ),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .height(70.dp)
                        .fillMaxWidth(0.8f)
                        .padding(16.dp)
                        .shadow(10.dp, shape = MaterialTheme.shapes.medium)
                ) {
                    Text(
                        text = "Зона!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }



    @Preview
    @Composable
    fun FirstOnBoardingScreenPreview() {
        Column(modifier = Modifier.fillMaxSize()) {
            PagerScreen(page = OnBoardingPage.First)
        }
    }

    @Preview
    @Composable
    fun SecondOnBoardingScreenPreview() {
        Column(modifier = Modifier.fillMaxSize()) {
            PagerScreen(page = OnBoardingPage.Second)
        }
    }

    @Preview
    @Composable
    fun ThirdOnBoardingScreenPreview() {
        Column(modifier = Modifier.fillMaxSize()) {
            PagerScreen(page = OnBoardingPage.Third)
        }
    }

    @Composable
    fun CustomHorizontalPagerIndicator(
        pagerState: PagerState,
        modifier: Modifier = Modifier,
        indicatorWidth: Dp = 8.dp,
        indicatorHeight: Dp = 8.dp,
        spacing: Dp = 4.dp
    ) {
        val activeColor = titleColor
        val inactiveColor = if (isSystemInDarkTheme()) StalkerGrayDark else StalkerGrayLight

        val pageCount = pagerState.pageCount
        val currentPage = pagerState.currentPage

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            for (index in 0 until pageCount) {
                Box(
                    modifier = Modifier
                        .size(indicatorWidth, indicatorHeight)
                        .background(
                            if (index == currentPage) activeColor else inactiveColor,
                            shape = CircleShape
                        )
                )
            }
        }
    }







