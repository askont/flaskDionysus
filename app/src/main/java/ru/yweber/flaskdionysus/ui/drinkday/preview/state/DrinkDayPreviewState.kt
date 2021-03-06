package ru.yweber.flaskdionysus.ui.drinkday.preview.state

/**
 * Created on 07.04.2020
 * @author YWeber */

data class DrinkDayPreviewState(
    val id: Int,
    val title: String,
    val imagePath: String,
    val drinkName: String,
    val rating: Int,
    val tried: String,
    val levelCooking: String,
    val alcoholStrength: String,
    val endShareAnimate: Boolean = false,
    val isStatusIba: Boolean = false,
    val isStatusHot: Boolean = false,
    val isStatusPuff: Boolean = false
)