package ru.yweber.flaskdionysus.ui.about

import android.os.Bundle
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
import toothpick.Scope
import toothpick.ktp.delegate.inject

/**
 * Created on 05.04.2020
 * @author YWeber */

class AboutProjectFragment : BaseFragment(R.layout.fragment_about_project) {

    private val viewModel by inject<AboutProjectViewModel>()


    override fun installModule(scope: Scope) {
        scope.installViewModel<AboutProjectViewModel>()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun backPressed(): Boolean {
        viewModel.backTo()
        return true
    }

}