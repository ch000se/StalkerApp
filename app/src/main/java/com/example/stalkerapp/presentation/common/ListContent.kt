package com.example.stalkerapp.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.stalkerapp.R
import com.example.stalkerapp.domain.model.Hero
import com.example.stalkerapp.navigation.Screen
import com.example.stalkerapp.presentation.components.RatingWidget
import com.example.stalkerapp.presentation.components.ShimmerEffect
import com.example.stalkerapp.ui.theme.descriptionColor
import com.example.stalkerapp.ui.theme.titleColor
import com.example.stalkerapp.util.Constants.BASE_URL

@Composable
fun ListContent(
    heroes: LazyPagingItems<Hero>,
    navController: NavHostController,
) {
    val result = handlePagingResult(heroes)
    if(result){
        Log.d("ListContent", heroes.loadState.toString())
        LazyColumn(
            contentPadding = PaddingValues(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(
                items = heroes.itemSnapshotList.items,
                key = { hero -> hero.id }
            ) { hero ->
                hero?.let {
                    HeroItem(
                        hero = it,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(
    heroes: LazyPagingItems<Hero>
): Boolean{
    heroes.apply(){
        val error = when {
            loadState.refresh is LoadState.Error -> {
                loadState.refresh as LoadState.Error
            }
            loadState.append is LoadState.Error -> {
                loadState.append as LoadState.Error
            }
            loadState.prepend is LoadState.Error -> {
                loadState.prepend as LoadState.Error
            }
            else -> {
                null
            }
        }
        return when {
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
                false
            }
            error != null -> {
                EmptyScreen(error = error, heroes = heroes)
                false
            }
            heroes.itemCount < 1 -> {
                EmptyScreen()
                false
            }
            else -> {
                true
            }
        }

    }
}


@Composable
fun HeroItem(
    hero: Hero,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .height(400.dp)
            .clickable {
                navController.navigate(Screen.Details.passHeroId(hero.id))
            },
        contentAlignment = Alignment.BottomStart
    ) {
        Surface(shape = RoundedCornerShape(16.dp)) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("$BASE_URL${hero.image}")
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .build(),
                contentDescription = stringResource(id = R.string.hero_image),
                contentScale = ContentScale.Crop
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = 0.7f),
            shape = RoundedCornerShape(
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = hero.name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = titleColor,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = hero.about,
                    style = MaterialTheme.typography.bodyMedium,
                    color = descriptionColor.copy(alpha = 0.7f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingWidget(
                        modifier = Modifier.padding(end = 8.dp),
                        rating = hero.rating
                    )
                    Text(
                        text = "${hero.rating}",
                        textAlign = TextAlign.Center,
                        color = descriptionColor.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HeroItemPreview() {
    HeroItem(
        hero = Hero(
            id = 1,
            name = "John Doe",
            image = " ",
            about = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla nec odio nec urna fermentum.",
            rating = 4.5,
            power = 100,
            month = "January",
            day = "1",
            family = emptyList(),
            abilities = emptyList(),
            natureTypes = emptyList(),
        ),
        navController = rememberNavController()
    )
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun HeroItemDarkPreview() {
    HeroItem(
        hero = Hero(
            id = 1,
            name = "John Doe",
            image = " ",
            about = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla nec odio nec urna fermentum.",
            rating = 4.5,
            power = 100,
            month = "January",
            day = "1",
            family = emptyList(),
            abilities = emptyList(),
            natureTypes = emptyList(),
        ),
        navController = rememberNavController()
    )
}
