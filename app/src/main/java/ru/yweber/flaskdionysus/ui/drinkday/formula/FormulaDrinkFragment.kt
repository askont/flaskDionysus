package ru.yweber.flaskdionysus.ui.drinkday.formula

import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
import toothpick.Scope
import toothpick.ktp.delegate.inject

/**
 * Created on 07.04.2020
 * @author YWeber */

class FormulaDrinkFragment : BaseFragment(R.layout.fragment_formula_drink) {

    private val viewModel by inject<FormulaDrinkViewModel>()

    override fun installModule(scope: Scope) {
        scope.installViewModel<FormulaDrinkViewModel>()
    }
}