package ru.yweber.flaskdionysus.ui

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.yweber.flaskdionysus.model.entity.ItemTypeFilter
import ru.yweber.flaskdionysus.ui.about.AboutProjectFragment
import ru.yweber.flaskdionysus.ui.detailed.DrinkDetailedFragment
import ru.yweber.flaskdionysus.ui.drinkday.DrinkTheDayFlowFragment
import ru.yweber.flaskdionysus.ui.drinkday.detailed.DrinkDayDetailedFragment
import ru.yweber.flaskdionysus.ui.drinkday.preview.DrinkDayPreviewFragment
import ru.yweber.flaskdionysus.ui.filter.FilterFragment
import ru.yweber.flaskdionysus.ui.filter.chooser.ChooserDialog
import ru.yweber.flaskdionysus.ui.home.HomeListDrinkFlowFragment
import ru.yweber.flaskdionysus.ui.main.MainFlowFragment
import ru.yweber.flaskdionysus.ui.search.SearchFragment

object Screens {

    object AboutProjectScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return AboutProjectFragment()
        }
    }

    object MainFlowScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return MainFlowFragment()
        }
    }

    object HomeListDrinkScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return HomeListDrinkFlowFragment()
        }
    }

    object DrinkTheDayFlowScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return DrinkTheDayFlowFragment()
        }
    }

    object DrinkDayPreviewScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return DrinkDayPreviewFragment()
        }
    }

    object DrinkDayDetailedScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return DrinkDayDetailedFragment()
        }
    }

    data class ChooserDialogHolder(private val type: ItemTypeFilter) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ChooserDialog.newInstance(type)
        }
    }

    object FilterScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return FilterFragment()
        }
    }

    data class DrinkDetailedScreen(private val drinkId: Int) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return DrinkDetailedFragment.newInstance(drinkId)
        }
    }

    object SearchScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return SearchFragment()
        }
    }

}