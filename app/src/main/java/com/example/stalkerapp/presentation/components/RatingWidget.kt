package com.example.stalkerapp.presentation.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.stalkerapp.R

@Composable
fun RatingWidget(
    modifier: Modifier,
    rating: Double,
    scaleFactor: Float = 2f,
    spaceBetween: Dp = 6.dp
) {
    val result = calculateStars(rating)
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween),
    ){
        result["filledStars"]?.let { filledStars ->
            repeat(filledStars){
                FilledStar(starPath, starPathBounds, scaleFactor)
            }
        }
        result["halfFilledStars"]?.let { halfFilledStars ->
            repeat(halfFilledStars){
                HalfFilledStar(starPath, starPathBounds, scaleFactor)
            }
        }
        result["emptyStars"]?.let { emptyStars ->
            repeat(emptyStars){
                EmptyStar(starPath, starPathBounds, scaleFactor)
            }
        }
    }
}

@Composable
fun FilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float = 1f
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size
        scale(scale = scaleFactor){
            val pathWidth = starPathBounds.width
            val pathHeight = starPathBounds.height
            val left = (canvasSize.width - pathWidth) / 2.3f
            val top = (canvasSize.height - pathHeight) / 2.3f
            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = Color.Yellow
                )
            }
        }
    }
}

@Composable
fun HalfFilledStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float = 1f
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size
        scale(scale = scaleFactor){
            val pathWidth = starPathBounds.width
            val pathHeight = starPathBounds.height
            val left = (canvasSize.width - pathWidth) / 2.3f
            val top = (canvasSize.height - pathHeight) / 2.3f
            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = Color.LightGray.copy(alpha = 0.5f)
                )
                clipPath(path = starPath){
                    drawRect(
                        color = Color.Yellow,
                        size = Size(
                            width = starPathBounds.maxDimension / 1.7f,
                            height = starPathBounds.maxDimension * scaleFactor + 1f
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactor: Float = 1f
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size
        scale(scale = scaleFactor){
            val pathWidth = starPathBounds.width
            val pathHeight = starPathBounds.height
            val left = (canvasSize.width - pathWidth) / 2.3f
            val top = (canvasSize.height - pathHeight) / 2.3f
            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = Color.LightGray
                )
            }
        }
    }
}
@Composable
fun calculateStars(rating: Double): Map<String, Int> {
    val maxStars = 5
    val filledStars = rating.toInt()
    val fractionalPart = rating - filledStars

    val halfFilledStars = if (fractionalPart in 0.25..0.75) 1 else 0
    val totalStars = filledStars + halfFilledStars
    val emptyStars = maxStars - totalStars

    return mapOf(
        "filledStars" to minOf(filledStars, maxStars),
        "halfFilledStars" to if (filledStars < maxStars) halfFilledStars else 0,
        "emptyStars" to maxOf(emptyStars, 0)
    )
}


@Composable
@Preview(showBackground = true)
fun FilledStarPreview(){
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    FilledStar(starPath, starPathBounds)
}

@Composable
@Preview(showBackground = true)
fun HalfFilledStarPreview(){
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    HalfFilledStar(starPath, starPathBounds)
}

@Composable
@Preview(showBackground = true)
fun EmptyStarPreview(){
    val starPathString = stringResource(id = R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(starPathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    EmptyStar(starPath, starPathBounds)
}
