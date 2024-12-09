package com.example.stalkerapp.domain.model

import androidx.annotation.DrawableRes
import com.example.stalkerapp.R

sealed class OnBoardingPage(@DrawableRes val image: Int, val title: String, val description: String) {
    data object First: OnBoardingPage(
        image = R.drawable.greetings,
        title = "Вітаю в Зоні",
        description = "Вітаю, сталкере, ти потрапив у цифровий КПК, де ти можеш дізнатись багато цікавого про мутантів, що живуть в Зоні."
    )
    data object Second: OnBoardingPage(
        image = R.drawable.explore,
        title = "Досліджуй",
        description = "Знайди мутанта, який тебе цікавить, та дізнайся де він проживає та що здатний ."
    )
    data object Third: OnBoardingPage(
        image = R.drawable.power,
        title = "Підготуйся",
        description = "Дізнайся секрети мутантів та їх слабкі сторони, щоб вижити в Зоні. Вдалого полювання, сталкере!"
    )
}