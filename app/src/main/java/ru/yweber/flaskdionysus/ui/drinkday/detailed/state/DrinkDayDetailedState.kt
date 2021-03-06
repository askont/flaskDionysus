package ru.yweber.flaskdionysus.ui.drinkday.detailed.state

import ru.yweber.flaskdionysus.core.adapter.state.DrinkDayItemState

/**
 * Created on 07.04.2020
 * @author YWeber */

data class DrinkDayDetailedState(
    val drinkName: String,
    val previewPath: String, // this path in animate and abroad image
    val pageItem: List<DrinkDayItemState>,
    val endShared: Boolean
)