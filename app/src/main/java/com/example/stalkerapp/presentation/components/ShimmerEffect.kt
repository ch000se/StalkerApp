package com.example.stalkerapp.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stalkerapp.ui.theme.descriptionColor
import com.example.stalkerapp.ui.theme.welcomeScreenBackground

@Composable
fun ShimmerEffect() {

    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(count = 2){
            AnimatedShimmerItem()
        }
    }
}

@Composable
fun AnimatedShimmerItem(){
    val transition = rememberInfiniteTransition()
    val alphaAnim = transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        ))
    ShimmerItem(alpha = alphaAnim.value)
}

@Composable
fun ShimmerItem(alpha: Float){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        color = welcomeScreenBackground,
        shape = RoundedCornerShape(size = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .alpha(alpha = alpha)
                    .height(20.dp),
                color = descriptionColor,
                shape = RoundedCornerShape(size = 10.dp)
            ) {  }
            Spacer(modifier = Modifier.padding(8.dp))
            repeat(3) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .alpha(alpha = alpha)
                        .height(10.dp),
                    color = descriptionColor,
                    shape = RoundedCornerShape(size = 10.dp)
                ) { }
                Spacer(modifier = Modifier.padding(4.dp))
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(5) {
                    Surface(
                        modifier = Modifier
                            .alpha(alpha = alpha)
                            .size(15.dp),
                        color = descriptionColor,
                        shape = RoundedCornerShape(size = 10.dp)
                    ) { }
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }

        }
    }

}


@Composable
@Preview
fun ShimmerItemPreview(){
    AnimatedShimmerItem()
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun ShimmerItemDarkPreview(){
    AnimatedShimmerItem()
}