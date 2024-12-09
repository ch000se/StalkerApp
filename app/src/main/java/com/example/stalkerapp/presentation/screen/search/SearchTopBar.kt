package com.example.stalkerapp.presentation.screen.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.stalkerapp.R
import com.example.stalkerapp.ui.theme.descriptionColor
import com.example.stalkerapp.ui.theme.topBarTitleColor
import com.example.stalkerapp.ui.theme.welcomeScreenBackground

@Composable
fun SearchTopBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    SearchWidget(text, onTextChange, onSearchClicked, onCloseClicked)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchWidget(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    // Використовуємо TopAppBar для пошукового інтерфейсу
    TopAppBar(
        title = {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth() // Заповнює весь доступний простір
                    .padding(horizontal = 8.dp), // Відступи для зручності
                value = text,
                onValueChange = { onTextChange(it) },
                placeholder = {
                    Text(
                        modifier = Modifier.alpha(0.6f),
                        text = "Пошук...",
                        color = Color.White
                    )
                },
                textStyle = TextStyle(color = topBarTitleColor),
                singleLine = true,
                leadingIcon = {
                    IconButton(
                        onClick = { /* Handle search action */ },
                        modifier = Modifier.alpha(0.6f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search_icon),
                            tint = descriptionColor
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (text.isNotEmpty()) {
                                onTextChange("")  // Очищення поля
                            } else {
                                onCloseClicked()  // Закриття
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.close_icon),
                            tint = descriptionColor
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearchClicked(text)  // Пошук при натисканні клавіші
                    }
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Gray,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.Gray,
                    focusedLeadingIconColor = descriptionColor,
                    unfocusedLeadingIconColor = descriptionColor,
                    focusedTrailingIconColor = descriptionColor,
                    unfocusedTrailingIconColor = descriptionColor
                )
            )
        },
        // Використовуємо властивості containerColor та titleContentColor для кольорів
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = welcomeScreenBackground,  // Колір фону
            titleContentColor = topBarTitleColor // Колір заголовка
        )
    )
}

@Composable
@Preview
fun SearchWidgetPreview() {
    SearchWidget(
        text = "",
        onTextChange = {},
        onSearchClicked = {},
        onCloseClicked = {}
    )
}