package com.example.stalkerapp.presentation.screens.details

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.stalkerapp.R
import com.example.stalkerapp.domain.model.Hero
import com.example.stalkerapp.presentation.components.InfoBox
import com.example.stalkerapp.presentation.components.OrderedList
import com.example.stalkerapp.ui.theme.descriptionColor
import com.example.stalkerapp.ui.theme.titleColor
import com.example.stalkerapp.util.Constants.BASE_URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsContent(
    navController: NavHostController,
    selectedHero: Hero?
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState()
    )

    val currentSheetFraction = scaffoldState.currentSheetFraction

    val radiusAnim by animateDpAsState(
        targetValue = if (currentSheetFraction == 1f) 40.dp else 0.dp
    )

    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(
            topStart = radiusAnim,
            topEnd = radiusAnim
        ),
        scaffoldState = scaffoldState,
        sheetPeekHeight = 150.dp,
        sheetContent = {
            selectedHero?.let {
                BottomSheetContent(selectedHero = it)
            }
        },
        content = {
            BackgroundContent(
                heroImage = selectedHero?.image ?: "",
                imageFraction = currentSheetFraction,
                onCloseClicked = { navController.popBackStack() }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    selectedHero: Hero,
    infoBoxIconColor: Color = MaterialTheme.colorScheme.primary,
    sheetBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = descriptionColor,
    welcomeScreenBackground: Color = MaterialTheme.colorScheme.background // цей параметр задає колір фону
) {
    Column(
        modifier = Modifier
            .fillMaxSize() // заповнюємо весь екран
            .background(welcomeScreenBackground) // фоновий колір для всієї колонки
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Тут ми прибираємо колір з іконки
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .weight(2f),
                painter = painterResource(id = R.drawable.nuclear__1_),
                contentDescription = stringResource(R.string.stalker_logo),
                tint = Color.Unspecified // прибираємо колір іконки
            )
            Text(
                modifier = Modifier
                    .weight(8f),
                text = selectedHero.name,
                color = contentColor,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(
                icon = painterResource(id = R.drawable.bolt),
                iconColor = infoBoxIconColor,
                bigText = "${selectedHero.power}",
                smallText = stringResource(R.string.power),
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.calendar),
                iconColor = infoBoxIconColor,
                bigText = selectedHero.month,
                smallText = stringResource(R.string.month),
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.cake),
                iconColor = infoBoxIconColor,
                bigText = selectedHero.day,
                smallText = stringResource(R.string.birthday),
                textColor = contentColor
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.about),
            color = contentColor,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold,
        )
        Text(
            modifier = Modifier
                .alpha(0.6f)
                .padding(bottom = 10.dp),
            text = selectedHero.about,
            color = contentColor,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            maxLines = 7
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            OrderedList(
                title = stringResource(R.string.family),
                items = selectedHero.family,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.abilities),
                items = selectedHero.abilities,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.nature_types),
                items = selectedHero.natureTypes,
                textColor = contentColor
            )
        }
    }
}

@Composable
fun BackgroundContent(
    heroImage: String,
    imageFraction: Float = 1f,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    onCloseClicked: () -> Unit
){
    val imageUrl = "$BASE_URL${heroImage}"
    val animatedImageSize by animateFloatAsState(
        targetValue = imageFraction,
        label = "Radius Animation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ){
       AsyncImage(
           modifier = Modifier
               .fillMaxWidth()
               .fillMaxHeight(fraction = (animatedImageSize + 0.1f))
                .align(Alignment.TopStart),
           model = ImageRequest.Builder(LocalContext.current)
               .data(imageUrl)
               .error(R.drawable.placeholder)
               .build(),
           contentDescription = stringResource(R.string.hero_image),
           contentScale = ContentScale.Crop
       )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier
                    .padding(10.dp),
                onClick = {onCloseClicked()}
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close_icon),
                    tint = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
val BottomSheetScaffoldState.currentSheetFraction: Float
    get() {
        val targetValue = bottomSheetState.targetValue
        val currentValue = bottomSheetState.currentValue

        return when {
            currentValue == SheetValue.Hidden && targetValue == SheetValue.Hidden -> 1f
            currentValue == SheetValue.Expanded && targetValue == SheetValue.Expanded -> 0f
            currentValue == SheetValue.Hidden && targetValue == SheetValue.Expanded -> 1f
            currentValue == SheetValue.Expanded && targetValue == SheetValue.Hidden -> 0f
            else -> 1f
        }
    }

@Composable
@Preview
fun BottomSheetContentPreview() {
    BottomSheetContent(
        selectedHero = Hero(
            id = 1,
            name = "Кровосос",
            image = "/images/Bloodsucker.png",
            about = "Кровососи — це мутанти, схожі на людей з особливістю: вони поглинають кров своїх жертв. Їхня здатність ставати невидимими робить їх небезпечними.",
            rating = 4.5,
            power = 80,
            month = "Березень",
            day = "10",
            family = listOf("Вампіроподібні"),
            abilities = listOf("Невидимість", "Поглинання крові", "Агресивність"),
            natureTypes = listOf("Наземний", "Мутант")
        )
    )
}
