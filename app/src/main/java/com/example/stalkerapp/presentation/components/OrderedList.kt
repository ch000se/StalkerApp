package com.example.stalkerapp.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color as ComposeColor
import com.example.stalkerapp.ui.theme.descriptionColor
import com.example.stalkerapp.ui.theme.welcomeScreenBackground // Переконайтеся, що цей колір визначено в вашій темі

@Composable
fun OrderedList(
    title: String,
    items: List<String>,
    textColor: ComposeColor
) {
    Column(
        modifier = Modifier
            .background(welcomeScreenBackground) // Застосовуємо колір фону
            .padding(16.dp) // Додаємо відступи для кращого вигляду
    ) {
        Text(
            modifier = Modifier.padding(bottom = 6.dp),
            text = title,
            color = textColor,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,  // Використовуємо titleMedium для Material3
            fontWeight = FontWeight.Bold
        )
        items.forEachIndexed { index, item ->
            Text(
                modifier = Modifier.alpha(0.6f),
                text = "${index + 1}. $item",
                color = textColor,
                fontSize = MaterialTheme.typography.bodyLarge.fontSize  // Використовуємо bodyLarge для Material3
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OrderedListPreview() {
    OrderedList(
        title = "Family",
        items = listOf("Item 1", "Item 2", "Item 3"),
        textColor = descriptionColor
    )
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun OrderedListDarkPreview() {
    OrderedList(
        title = "Family",
        items = listOf("Item 1", "Item 2", "Item 3"),
        textColor = descriptionColor
    )
}
