package com.example.stalkerapp.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stalkerapp.R
import com.example.stalkerapp.ui.theme.descriptionColor
import com.example.stalkerapp.ui.theme.welcomeScreenBackground // Ensure this color is defined in your theme

@Composable
fun InfoBox(
    icon: Painter,
    iconColor: androidx.compose.ui.graphics.Color,
    bigText: String,
    smallText: String,
    textColor: ComposeColor
) {
    Row(
        modifier = Modifier
            .background(welcomeScreenBackground)
            .padding(16.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = stringResource(R.string.info_icon),
            tint = ComposeColor.Unspecified,
            modifier = Modifier.size(48.dp)
        )
        Column {
            Text(
                text = bigText,
                color = textColor,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,  // Use titleLarge or other appropriate typography
                fontWeight = FontWeight.Black
            )
            Text(
                modifier = Modifier.alpha(0.6f),
                text = smallText,
                color = textColor,
                fontSize = MaterialTheme.typography.bodySmall.fontSize  // Use bodySmall instead of overline
            )
        }
    }
}

@Composable
@Preview
fun InfoBoxPreview() {
    InfoBox(
        icon = painterResource(id = R.drawable.bolt),
        iconColor = MaterialTheme.colorScheme.primary, // Use colorScheme instead of colors
        bigText = "92",
        smallText = "Power",
        textColor = descriptionColor
    )
}


@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun InfoBoxDarkPreview() {
    InfoBox(
        icon = painterResource(id = R.drawable.bolt),
        iconColor = MaterialTheme.colorScheme.primary, // Use colorScheme instead of colors
        bigText = "92",
        smallText = "Power",
        textColor = descriptionColor
    )
}
