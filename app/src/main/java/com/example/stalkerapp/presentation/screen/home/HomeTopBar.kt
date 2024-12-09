    package com.example.stalkerapp.presentation.screen.home

    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Search
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
    import androidx.compose.material3.Text
    import androidx.compose.material3.TopAppBar
    import androidx.compose.material3.TopAppBarDefaults
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.res.stringResource
    import androidx.compose.ui.tooling.preview.Preview
    import com.example.stalkerapp.R
    import com.example.stalkerapp.ui.theme.StalkerGrayDark
    import com.example.stalkerapp.ui.theme.descriptionColor
    import com.example.stalkerapp.ui.theme.titleColor
    import com.example.stalkerapp.ui.theme.topBarBackgroundColor
    import com.example.stalkerapp.ui.theme.topBarTitleColor
    import com.example.stalkerapp.ui.theme.welcomeScreenBackground

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HomeTopBar(onSearchedClick: () -> Unit) {
        TopAppBar(
            title = {
                Text(
                    text = "Пошук",
                    color = descriptionColor
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = welcomeScreenBackground
            ),
            actions = {
                IconButton(onClick = onSearchedClick) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search),
                        tint = descriptionColor
                    )
                }
            }
        )
    }

    @Composable
    @Preview
    fun HomeTopBarPreview() {
        HomeTopBar(onSearchedClick = {})
    }
